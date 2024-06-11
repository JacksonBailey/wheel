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
        options.compilerArgs.add("-Xlint:all")
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
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
