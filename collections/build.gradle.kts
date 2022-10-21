plugins {
    id("dev.jacksonbailey.wheel.java-library-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
}

dependencies {
    api(platform(project(":platform")))
    compileOnly("org.jetbrains:annotations")
    implementation("org.slf4j:slf4j-api")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-inline")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testRuntimeOnly("org.slf4j:slf4j-simple")
}
