plugins {
    id("dev.jacksonbailey.wheel.base-common-conventions")
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:2.7.4"))
    api("org.jetbrains:annotations:23.0.0")
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
