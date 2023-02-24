plugins {
    id("dev.jacksonbailey.wheel.java-library-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
}

dependencies {
    implementation("org.slf4j:slf4j-api")
    implementation(platform(project(":platform")))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.slf4j:slf4j-simple")
}
