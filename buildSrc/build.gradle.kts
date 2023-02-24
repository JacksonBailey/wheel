plugins {
    idea
    `kotlin-dsl`
}

idea {
    module {
        isDownloadSources = true
    }
}

repositories {
    gradlePluginPortal()
}

kotlin {
    jvmToolchain {
        this.languageVersion.set(JavaLanguageVersion.of(19))
    }
}
