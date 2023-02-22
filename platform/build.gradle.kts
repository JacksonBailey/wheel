plugins {
    id("dev.jacksonbailey.wheel.java-platform-conventions")
}

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:3.0.2")) // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-dependencies
    api(libs.protobuf.bom)
    api(libs.grpc.all)
    api(libs.sqlite.jdbc)
    api("javax.annotation:javax.annotation-api:1.3.2") // https://mvnrepository.com/artifact/javax.annotation/javax.annotation-api
    api("org.jetbrains:annotations:24.0.0") // https://mvnrepository.com/artifact/org.jetbrains/annotations
}
