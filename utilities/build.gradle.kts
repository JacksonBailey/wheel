plugins {
    id("dev.jacksonbailey.wheel.java-library-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
}

dependencies {
    api(platform(project(":platform")))
    api(project(":list"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
