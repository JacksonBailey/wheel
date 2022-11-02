plugins {
    id("dev.jacksonbailey.wheel.java-platform-conventions")
}

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:2.7.5"))
    api("org.jetbrains:annotations:23.0.0")
}
