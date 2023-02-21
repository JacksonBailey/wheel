import nu.studer.gradle.jooq.JooqGenerate
import org.flywaydb.gradle.task.AbstractFlywayTask
import java.sql.DriverManager;

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
    implementation(platform(project(":platform")))

    compileOnly("com.google.code.findbugs:jsr305")
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("jakarta.validation:jakarta.validation-api")

    jooqGenerator(libs.sqlite.jdbc)
}

val dbFile = file("$buildDir/generated/db/vexillum.sqlite") // TODO Where to store this file?
val jdbcUrl = "jdbc:sqlite:${dbFile.path}"

tasks {
    val createDbFile = register("createDbFile") {
        // Creates the database file if it does not exist.

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
    }
}

flyway {
    url = jdbcUrl
}

jooq {
    version.set(libs.versions.jooqVersion)
    configurations {
        create("main") {
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.sqlite.JDBC"
                    url = jdbcUrl
                    initScript = "PRAGMA foreign_keys = ON;"
                }
                generator.apply {
                    name = "org.jooq.codegen.JavaGenerator"
                    database.apply {
                        name = "org.jooq.meta.sqlite.SQLiteDatabase"
                        excludes = "flyway_schema_history"
                    }
                    generate.apply {
                        isGeneratedAnnotation = true
                        isNullableAnnotation = true
                        isNonnullAnnotation = true
                        isJpaAnnotations = true
                        isValidationAnnotations = true
                        isConstructorPropertiesAnnotation = true
                        isPojos = true
                        isPojosAsJavaRecordClasses = true;
                    }
                    target.packageName = "dev.jacksonbailey.vexillum.db.jooq"
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

tasks.withType<JooqGenerate> {
    // TODO Check if this is right
    inputs.file(dbFile)
    allInputsDeclared.set(true)
}
