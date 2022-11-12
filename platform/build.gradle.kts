plugins {
    id("dev.jacksonbailey.wheel.java-platform-conventions")
}

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:2.7.5"))
    api(libs.protobuf.bom)
    api(libs.grpc.all)
    api("org.jetbrains:annotations:23.0.0")
}
