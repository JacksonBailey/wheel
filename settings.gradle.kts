rootProject.name = "wheel"
include("platform", "collections")

// https://docs.gradle.org/current/userguide/declaring_repositories.html#sub:centralized-repository-declaration
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
