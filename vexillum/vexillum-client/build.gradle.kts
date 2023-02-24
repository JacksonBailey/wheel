plugins {
    id("dev.jacksonbailey.wheel.java-library-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
}

dependencies {
    api("io.grpc:grpc-api")
    compileOnly("org.jetbrains:annotations")
    implementation("org.slf4j:slf4j-api")
    implementation(platform(project(":platform")))
    implementation(project(":vexillum:vexillum-api"))
    testImplementation("io.grpc:grpc-core")
    testImplementation("io.grpc:grpc-stub")
    testImplementation("io.grpc:grpc-testing")
    testImplementation("junit:junit") // TODO Because GrpcCleanup uses JUnit 4 rule
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.mockito:mockito-core")
    testRuntimeOnly("org.slf4j:slf4j-simple")
}
