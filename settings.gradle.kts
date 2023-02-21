rootProject.name = "wheel"
include(
    "platform",
    "collections",
    "vexillum",
    "vexillum:vexillum-api",
    "vexillum:vexillum-client",
    "vexillum:vexillum-core",
    "vexillum:vexillum-db",
    "vexillum:vexillum-server",
    "vexillum:vexillum-sqlite"
)

dependencyResolutionManagement {
    // https://docs.gradle.org/current/userguide/declaring_repositories.html#sub:centralized-repository-declaration
    repositories {
        mavenCentral()
    }

    // Declared outside :platform so protobuf plugin can use the same versions as depencencies
    versionCatalogs {
        create("libs") {
            val protobufVersion = version("protobufVersion", "3.22.0") // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-bom
            val grpcVersion = version("grpcVersion", "1.53.0") // https://mvnrepository.com/artifact/io.grpc/grpc-all
            val sqliteJdbcVersion = version("sqliteJdbcVersion", "3.40.1.0") // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
            val jooqVersion = version("jooqVersion", "3.17.8") // https://mvnrepository.com/artifact/org.jooq/jooq

            library("protobuf-bom", "com.google.protobuf", "protobuf-bom").versionRef(protobufVersion)
            library("grpc-all", "io.grpc", "grpc-all").versionRef(grpcVersion)
            library("sqlite-jdbc", "org.xerial", "sqlite-jdbc").versionRef(sqliteJdbcVersion)
            library("jooq", "org.jooq", "jooq").versionRef(jooqVersion)
        }
    }
}
