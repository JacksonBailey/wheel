plugins {
    id("dev.jacksonbailey.wheel.base-common-conventions")
    jacoco
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withJavadocJar()
    withSourcesJar()
}

jacoco {
    toolVersion = "0.8.12" // https://www.jacoco.org/jacoco/
}

tasks {
    withType<JavaCompile> {
        // TODO Check that -g is actually adding debug info because it seems like it's already there
        options.compilerArgs.addAll(listOf("-Xlint:all", "-g"))
    }

    test {
        useJUnitPlatform()
        finalizedBy(tasks.jacocoTestReport)
    }

    jacocoTestReport {
        dependsOn(tasks.test)
    }

    /*
     * Using jar { } will only apply to tasks named "jar" so the license would not end up in the
     * javadoc jar nor source jar.
     */
    withType<Jar> {
        metaInf {
            from(layout.projectDirectory.file("LICENSE"))
            from(layout.projectDirectory.file("LICENSE.LESSER"))
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
