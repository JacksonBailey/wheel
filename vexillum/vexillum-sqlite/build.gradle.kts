import nu.studer.gradle.jooq.JooqGenerate
import org.flywaydb.gradle.task.AbstractFlywayTask
import java.sql.DriverManager

plugins {
    id("dev.jacksonbailey.wheel.java-library-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
    id("nu.studer.jooq")
    id("org.flywaydb.flyway")
}

buildscript {
    dependencies {
        classpath(libs.sqlite.jdbc)
    }
}

dependencies {
    api(libs.jooq)
    api(project(":vexillum:vexillum-core"))
    compileOnly("org.jetbrains:annotations")
    implementation("org.flywaydb:flyway-core")
    implementation("org.slf4j:slf4j-api")
    implementation(platform(project(":platform")))
    jooqGenerator(libs.sqlite.jdbc)
    runtimeOnly("org.slf4j:slf4j-simple")
    runtimeOnly("org.xerial:sqlite-jdbc")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
}

val sqliteInitScript = "PRAGMA foreign_keys = ON;"
val dbFile = file("${layout.buildDirectory.asFile.get().path}/generated/db/vexillum.sqlite") // TODO Where to store this file?
val jdbcUrl = "jdbc:sqlite:${dbFile.path}"
val flywayMigrationLocationOnDisk = file("$projectDir/src/main/resources/db/migration")
val jooqOutputDir = file("${layout.buildDirectory.asFile.get().path}/generated/sources/jooq")

flyway {
    url = jdbcUrl
    initSql = sqliteInitScript
    locations = arrayOf("filesystem:${flywayMigrationLocationOnDisk.path}")
}

jooq {
    version.set(libs.versions.jooqVersion)
    configurations {
        create("main") {
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.sqlite.JDBC"
                    url = jdbcUrl
                    initScript = sqliteInitScript
                }
                generator.apply {
                    name = "org.jooq.codegen.JavaGenerator"
                    database.apply {
                        name = "org.jooq.meta.sqlite.SQLiteDatabase"
                        excludes = "flyway_schema_history"
                    }
                    generate.apply {
                        isGeneratedAnnotation = true
                    }
                    target.apply {
                        packageName = "dev.jacksonbailey.wheel.vexillum.sqlite.jooq"
                        directory = jooqOutputDir.path
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

tasks {
    val createDbFile = register("createDbFile") {
        // Creates the database file if it does not exist.
        inputs.dir(flywayMigrationLocationOnDisk)
        outputs.file(dbFile)

        // TODO Make this a plugin? https://docs.gradle.org/current/userguide/tutorial_using_tasks.html#sec:build_script_external_dependencies
        doLast {
            dbFile.parentFile.mkdirs()
            DriverManager.getConnection(jdbcUrl).close()
        }
    }
    withType<AbstractFlywayTask> {
        dependsOn(createDbFile)
    }
    withType<JooqGenerate> {
        dependsOn(createDbFile, "flywayMigrate")
        inputs.file(dbFile)
        inputs.dir(flywayMigrationLocationOnDisk)
        outputs.dir(jooqOutputDir)
        allInputsDeclared.set(true)
    }
}
