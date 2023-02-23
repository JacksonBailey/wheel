plugins {
    id("dev.jacksonbailey.wheel.java-library-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
}


dependencies {
    implementation(platform(project(":platform")))
    implementation(project(":vexillum:vexillum-core"))
    implementation(project(":vexillum:vexillum-sqlite"))

    implementation("org.flywaydb:flyway-core")
    implementation("org.slf4j:slf4j-api")
    implementation(libs.jooq)

    runtimeOnly("org.slf4j:slf4j-simple")
    runtimeOnly("org.xerial:sqlite-jdbc")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")

    testRuntimeOnly("org.slf4j:slf4j-simple")
}
