import com.google.protobuf.gradle.id

plugins {
    id("dev.jacksonbailey.wheel.java-library-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
    id("com.google.protobuf") version "0.9.1"
}

dependencies {
    implementation(platform(project(":platform")))

    compileOnly("javax.annotation:javax.annotation-api")

    api("com.google.protobuf:protobuf-java")
    api("io.grpc:grpc-api")

    implementation("io.grpc:grpc-protobuf")

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
        // all() to configure all
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc") {}
            }
        }
    }
}
