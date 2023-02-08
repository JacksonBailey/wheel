import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.dorongold.task-tree") version "2.1.1" // https://github.com/dorongold/gradle-task-tree#usage
    id("com.github.ben-manes.versions") version "0.45.0" // https://github.com/ben-manes/gradle-versions-plugin#dependencyupdates
    id("com.autonomousapps.dependency-analysis") version "1.19.0" // https://github.com/autonomousapps/dependency-analysis-android-gradle-plugin#add-to-your-project-and-use
}

allprojects {
    group = "dev.jacksonbailey.wheel"
}

tasks.withType(DependencyUpdatesTask::class) {
    // TODO Consider adding dependencyUpdates to build?
    revision = "release"
}
