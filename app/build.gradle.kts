plugins {
    id("dev.jacksonbailey.wheel.java-application-conventions")
}

dependencies {
    implementation("org.apache.commons:commons-text")
    implementation(project(":utilities"))
}

application {
    mainClass.set("dev.jacksonbailey.wheel.app.App")
}
