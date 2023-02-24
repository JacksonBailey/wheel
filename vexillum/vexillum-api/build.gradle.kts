import com.google.protobuf.gradle.id

plugins {
    id("com.google.protobuf")
    id("dev.jacksonbailey.wheel.java-library-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
}

dependencies {
    api("com.google.protobuf:protobuf-java")
    api("io.grpc:grpc-api")
    compileOnly("javax.annotation:javax.annotation-api")
    implementation("io.grpc:grpc-protobuf")
    implementation(platform(project(":platform")))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${libs.versions.protobufVersion.get()}"
    }
    plugins {
        register("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${libs.versions.grpcVersion.get()}"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                id("grpc") {}
            }
        }
    }
}
