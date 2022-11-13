plugins {
    id("dev.jacksonbailey.wheel.java-library-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
}

dependencies {
    api(platform(project(":platform")))

    compileOnly("org.jetbrains:annotations")

    testImplementation("org.assertj:assertj-core")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
}
