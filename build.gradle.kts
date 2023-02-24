import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.autonomousapps.dependency-analysis")
    id("com.dorongold.task-tree")
    id("com.github.ben-manes.versions")
}

allprojects {
    group = "dev.jacksonbailey.wheel"
}

tasks.withType(DependencyUpdatesTask::class) {

    revision = "release"
    gradleReleaseChannel = "current"

    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
            version.toUpperCase().contains(it)
        }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }

    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}
