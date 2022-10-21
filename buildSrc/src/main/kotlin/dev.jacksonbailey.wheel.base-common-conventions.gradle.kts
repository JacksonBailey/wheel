plugins {
    idea
    `maven-publish`
    signing
}

idea {
    module {
        isDownloadSources = true
    }
}

extra["isReleaseVersion"] = !version.toString().endsWith("SNAPSHOT")

tasks {
    withType<Sign>().configureEach {
        onlyIf { project.extra["isReleaseVersion"] as Boolean }
    }
}

publishing {
    publications {
        withType<MavenPublication>() {
            pom {
                val gitHubUrl = "https://github.com/JacksonBailey/wheel"
                name.set("${groupId}:${artifactId}")
                description.set("Reinventing ${artifactId}")
                url.set(gitHubUrl)
                licenses {
                    license {
                        name.set("LGPL-3.0-or-later")
                        url.set("https://www.gnu.org/licenses/lgpl-3.0.txt")
                    }
                }
                developers {
                    developer {
                        name.set("Jackson Bailey")
                        url.set("https://jacksonbailey.dev")
                    }
                }
                scm {
                    connection.set("scm:git:${gitHubUrl}.git")
                    developerConnection.set("scm:git:${gitHubUrl}.git")
                    url.set(gitHubUrl)
                }
            }
        }
    }
}

signing {
    useGpgCmd()
}
