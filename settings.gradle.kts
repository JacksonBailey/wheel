rootProject.name = "wheel"

include(
    "collections",
    "platform",
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
            if (requested.id.id == "com.autonomousapps.dependency-analysis") { useVersion("1.19.0") }
            if (requested.id.id == "com.dorongold.task-tree") { useVersion("2.1.1") }
            if (requested.id.id == "com.github.ben-manes.versions") { useVersion("0.46.0") }
            if (requested.id.id == "com.google.protobuf") { useVersion("0.9.2") }
            if (requested.id.id == "nu.studer.jooq") { useVersion("8.1") }
            if (requested.id.id == "org.flywaydb.flyway") { useVersion("9.15.1") }
        }
    }
}

dependencyResolutionManagement {
    // https://docs.gradle.org/current/userguide/declaring_repositories.html#sub:centralized-repository-declaration
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            val grpcVersion = version("grpcVersion", "1.53.0") // https://mvnrepository.com/artifact/io.grpc/grpc-all
            val jooqVersion = version("jooqVersion", "3.17.8") // https://mvnrepository.com/artifact/org.jooq/jooq
            val protobufVersion = version("protobufVersion", "3.22.0") // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-bom
            val sqliteJdbcVersion = version("sqliteJdbcVersion", "3.41.0.0") // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc

            library("grpc-all", "io.grpc", "grpc-all").versionRef(grpcVersion)
            library("jooq", "org.jooq", "jooq").versionRef(jooqVersion)
            library("protobuf-bom", "com.google.protobuf", "protobuf-bom").versionRef(protobufVersion)
            library("sqlite-jdbc", "org.xerial", "sqlite-jdbc").versionRef(sqliteJdbcVersion)
        }
    }
}
