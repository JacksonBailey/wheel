plugins {
    id("dev.jacksonbailey.wheel.java-application-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
}

dependencies {
    implementation(platform(project(":platform")))
    implementation("org.apache.commons:commons-text")
    implementation(project(":utilities"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("dev.jacksonbailey.wheel.app.App")
}
