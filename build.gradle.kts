import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.dorongold.task-tree") version "2.1.0" // https://github.com/dorongold/gradle-task-tree#usage
    id("com.github.ben-manes.versions") version "0.44.0" // https://github.com/ben-manes/gradle-versions-plugin#dependencyupdates
}

allprojects {
    group = "dev.jacksonbailey.wheel"
}

tasks.withType(DependencyUpdatesTask::class) {
    // TODO Consider adding dependencyUpdates to build?
    revision = "release"
}
