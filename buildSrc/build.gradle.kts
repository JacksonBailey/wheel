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
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(19))
    }
}
