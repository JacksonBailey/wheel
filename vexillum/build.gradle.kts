import com.google.protobuf.gradle.id

plugins {
    id("dev.jacksonbailey.wheel.java-application-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
    id("com.google.protobuf") version "0.9.1"
}


dependencies {
    implementation(platform(project(":platform")))

    implementation("com.google.protobuf:protobuf-java")
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-stub")
    implementation("javax.annotation:javax.annotation-api")
    implementation("org.slf4j:slf4j-api")

    testImplementation("io.grpc:grpc-testing")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")

    testRuntimeOnly("org.slf4j:slf4j-simple")
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
