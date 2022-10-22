plugins {
    id("dev.jacksonbailey.wheel.base-common-conventions")
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

tasks {
    assemble {
        dependsOn(tasks.withType<GenerateMavenPom>(), tasks.withType<GenerateModuleMetadata>())
    }
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
