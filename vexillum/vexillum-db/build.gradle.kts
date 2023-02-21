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
    implementation("org.xerial:sqlite-jdbc")
    implementation(libs.jooq)

    runtimeOnly("org.slf4j:slf4j-simple")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")

}
