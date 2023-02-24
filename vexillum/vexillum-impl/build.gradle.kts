plugins {
    id("dev.jacksonbailey.wheel.java-library-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
}


dependencies {
    api(project(":vexillum:vexillum-core"))

    implementation(platform(project(":platform")))

    runtimeOnly("org.flywaydb:flyway-core")
    runtimeOnly("org.slf4j:slf4j-simple")
    runtimeOnly("org.xerial:sqlite-jdbc")
    runtimeOnly(project(":vexillum:vexillum-sqlite"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")

    testRuntimeOnly("org.slf4j:slf4j-simple")
}
