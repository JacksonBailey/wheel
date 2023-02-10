plugins {
    id("dev.jacksonbailey.wheel.java-library-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
}


dependencies {
    implementation(platform(project(":platform")))
    implementation(project(":vexillum:vexillum-core"))
    implementation(project(":vexillum:vexillum-jooq-codegen"))

    implementation("org.xerial:sqlite-jdbc")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")

    testRuntimeOnly("org.slf4j:slf4j-simple")
}
