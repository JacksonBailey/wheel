plugins {
    id("dev.jacksonbailey.wheel.base-common-conventions")
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withJavadocJar()
    withSourcesJar()
}

tasks {
    withType<JavaCompile> {
        // TODO Check that -g is actually adding debug info because it seems like it's already there
        options.compilerArgs.addAll(listOf("-Xlint:all", "-g"))
    }

    named<Test>("test") {
        useJUnitPlatform()
    }

    withType<Jar> {
        metaInf {
            from(layout.projectDirectory.file("LICENSE"))
        }
    }

    javadoc {
        options {
            (this as CoreJavadocOptions).addMultilineStringsOption("tag").value = listOf(
                "apiNote:a:API Note:",
                "implSpec:a:Implementation Requirements:",
                "implNote:a:Implementation Note:"
            )
        }
    }

    assemble {
        dependsOn(tasks.withType<GenerateMavenPom>(), tasks.withType<GenerateModuleMetadata>())
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            // TODO Look more into this https://docs.gradle.org/8.8/userguide/publishing_maven.html#publishing_maven:resolved_dependencies
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
