pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.gradle.toolchains.foojay-resolver-convention") { useVersion("0.8.0") } // https://github.com/gradle/foojay-toolchains/tags
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention")
}
