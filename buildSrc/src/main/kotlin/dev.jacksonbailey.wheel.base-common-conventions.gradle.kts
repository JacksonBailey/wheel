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

repositories {
    mavenCentral()
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
                val gitHubUrl = "https://github.com/JacksonBailey/wheel"
                name.set("${groupId}:${artifactId}")
                description.set("Reinventing ${artifactId}")
                url.set(gitHubUrl)
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
                    connection.set("scm:git:${gitHubUrl}.git")
                    developerConnection.set("scm:git:${gitHubUrl}.git")
                    url.set(validUri(gitHubUrl))
                }
            }
        }
    }
}

signing {
    useGpgCmd()
}
