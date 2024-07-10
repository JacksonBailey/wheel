import java.net.URI

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
        onlyIf {
            project.extra["isReleaseVersion"] as Boolean
        }
    }
}

val licenseName: String by project
val licenseUrl: String by project

fun validUri(string: String): String {
    return URI.create(string).toString()
}

publishing {
    publications {
        withType<MavenPublication>() {
            pom {
                val codebergUrl = "https://codeberg.org/JacksonBailey/wheel"
                name.set("${groupId}:${artifactId}")
                description.set("Reinventing ${artifactId}")
                url.set(codebergUrl)
                licenses {
                    license {
                        name.set(licenseName)
                        url.set(validUri(licenseUrl))
                    }
                }
                developers {
                    developer {
                        name.set("Jackson Bailey")
                        url.set(validUri("https://jacksonbailey.dev"))
                    }
                }
                scm {
                    connection.set("scm:git:${codebergUrl}.git")
                    developerConnection.set("scm:git:${codebergUrl}.git")
                    url.set(validUri(codebergUrl))
                }
            }
        }
    }
}

signing {
    useGpgCmd()
}
