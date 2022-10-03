plugins {
    id("dev.jacksonbailey.wheel.base-common-conventions")
    java
    `maven-publish`
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }
    withJavadocJar()
    withSourcesJar()
}

tasks {

    named<Test>("test") {
        useJUnitPlatform()
    }

    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
