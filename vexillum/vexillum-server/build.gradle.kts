plugins {
    id("dev.jacksonbailey.wheel.java-application-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
}


dependencies {
    implementation(platform(project(":platform")))
    implementation(project(":vexillum:vexillum-api"))

    implementation("io.grpc:grpc-api")
    implementation("io.grpc:grpc-stub")
    implementation("org.slf4j:slf4j-api")

    runtimeOnly("org.slf4j:slf4j-simple")

    testImplementation("io.grpc:grpc-core")
    testImplementation("io.grpc:grpc-testing")
    testImplementation("junit:junit") // TODO Because GrpcCleanup uses JUnit 4 rule
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")

    testRuntimeOnly("org.slf4j:slf4j-simple")
}
