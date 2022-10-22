plugins {
    id("dev.jacksonbailey.wheel.base-common-conventions")
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

publishing {
    publications {
        create<MavenPublication>("mavenPlatform") {
            from(components["javaPlatform"])
        }
    }
}

signing {
    sign(publishing.publications["mavenPlatform"])
}
