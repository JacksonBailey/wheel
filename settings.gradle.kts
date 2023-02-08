rootProject.name = "wheel"
include(
    "platform",
    "collections",
    "vexillum",
    "vexillum:vexillum-api",
    "vexillum:vexillum-client",
    "vexillum:vexillum-server"
)

dependencyResolutionManagement {
    // https://docs.gradle.org/current/userguide/declaring_repositories.html#sub:centralized-repository-declaration
    repositories {
        mavenCentral()
    }

    // Declared outside :platform so protobuf plugin can use the same versions as depencencies
    versionCatalogs {
        create("libs") {
            version("protobufVersion", "3.21.12") // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-bom
            version("grpcVersion", "1.52.1") // https://mvnrepository.com/artifact/io.grpc/grpc-all

            library("protobuf-bom", "com.google.protobuf", "protobuf-bom").versionRef("protobufVersion")
            library("grpc-all", "io.grpc", "grpc-all").versionRef("grpcVersion")
        }
    }
}
