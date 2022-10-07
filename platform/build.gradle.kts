plugins {
    `java-platform`
    `maven-publish`
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
        create<MavenPublication>("platform") {
            from(components["javaPlatform"])
        }
    }
}
