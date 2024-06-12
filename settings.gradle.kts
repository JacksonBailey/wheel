rootProject.name = "wheel"

include(
    "collections",
    "platform",
    "terra",
    "vexillum",
    "vexillum:vexillum-api",
    "vexillum:vexillum-client",
    "vexillum:vexillum-core",
    "vexillum:vexillum-impl",
    "vexillum:vexillum-server",
    "vexillum:vexillum-sqlite",
)

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.autonomousapps.dependency-analysis") { useVersion("1.32.0") } // https://github.com/autonomousapps/dependency-analysis-gradle-plugin/tags
            if (requested.id.id == "com.dorongold.task-tree") { useVersion("3.0.0") } // https://github.com/dorongold/gradle-task-tree/releases
            if (requested.id.id == "com.github.ben-manes.versions") { useVersion("0.51.0") } // https://github.com/ben-manes/gradle-versions-plugin/releases
            if (requested.id.id == "com.google.protobuf") { useVersion("0.9.4") } // https://github.com/google/protobuf-gradle-plugin/releases
            if (requested.id.id == "nu.studer.jooq") { useVersion("9.0") } // https://github.com/etiennestuder/gradle-jooq-plugin/releases
            if (requested.id.id == "org.flywaydb.flyway") { useVersion("9.22.3") } // https://github.com/flyway/flyway/releases
            if (requested.id.id == "org.gradle.toolchains.foojay-resolver-convention") { useVersion("0.8.0") } // https://github.com/gradle/foojay-toolchains/tags
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention")
}

dependencyResolutionManagement {
    // https://docs.gradle.org/current/userguide/declaring_repositories.html#sub:centralized-repository-declaration
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            val grpcVersion = version("grpcVersion", "1.64.0") // https://mvnrepository.com/artifact/io.grpc/grpc-all
            val jooqVersion = version("jooqVersion", "3.19.9") // https://mvnrepository.com/artifact/org.jooq/jooq
            val protobufVersion = version("protobufVersion", "4.27.1") // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-bom
            val sqliteJdbcVersion = version("sqliteJdbcVersion", "3.46.0.0") // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc

            library("grpc-all", "io.grpc", "grpc-all").versionRef(grpcVersion)
            library("jooq", "org.jooq", "jooq").versionRef(jooqVersion)
            library("protobuf-bom", "com.google.protobuf", "protobuf-bom").versionRef(protobufVersion)
            library("sqlite-jdbc", "org.xerial", "sqlite-jdbc").versionRef(sqliteJdbcVersion)
        }
    }
}
