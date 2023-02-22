import nu.studer.gradle.jooq.JooqGenerate
import org.flywaydb.gradle.task.AbstractFlywayTask
import java.sql.DriverManager

plugins {
    id("dev.jacksonbailey.wheel.java-library-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
    id("nu.studer.jooq") version "8.1"
    id("org.flywaydb.flyway") version "9.14.1"
}

buildscript {
    dependencies {
        classpath(libs.sqlite.jdbc)
    }
}

dependencies {
    api(project(":vexillum:vexillum-core"))

    compileOnly("org.jetbrains:annotations")

    implementation(platform(project(":platform")))

    implementation("org.flywaydb:flyway-core")
    implementation("org.slf4j:slf4j-api")
    implementation(libs.jooq)

    runtimeOnly("org.slf4j:slf4j-simple")
    runtimeOnly("org.xerial:sqlite-jdbc")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")

    jooqGenerator(libs.sqlite.jdbc)
}

val sqliteInitScript = "PRAGMA foreign_keys = ON;"
val dbFile = file("$buildDir/generated/db/vexillum.sqlite") // TODO Where to store this file?
val jdbcUrl = "jdbc:sqlite:${dbFile.path}"
val flywayMigrationLocation = file("$projectDir/src/main/resources/db/migration")
val jooqOutputDir = file("$buildDir/generated/sources/jooq")

tasks {
    val createDbFile = register("createDbFile") {
        // Creates the database file if it does not exist.
        inputs.dir(flywayMigrationLocation)
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
        inputs.dir(flywayMigrationLocation)
        outputs.dir(jooqOutputDir)
        allInputsDeclared.set(true)
    }
}

flyway {
    url = jdbcUrl
    initSql = sqliteInitScript
    locations = arrayOf("filesystem:${flywayMigrationLocation.path}")
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
                        packageName = "dev.jacksonbailey.vexillum.db.jooq"
                        directory = jooqOutputDir.path
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}