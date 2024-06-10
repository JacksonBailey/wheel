plugins {
    id("dev.jacksonbailey.wheel.java-platform-conventions")
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api("jakarta.annotation:jakarta.annotation-api:3.0.0") // https://mvnrepository.com/artifact/jakarta.annotation/jakarta.annotation-api
    api("javax.annotation:javax.annotation-api:1.3.2") // https://mvnrepository.com/artifact/javax.annotation/javax.annotation-api
    api("org.assertj:assertj-core:3.26.0") // https://mvnrepository.com/artifact/org.assertj/assertj-core
    api("org.flywaydb:flyway-core:9.22.3") // https://mvnrepository.com/artifact/org.flywaydb/flyway-core
    api("org.jetbrains:annotations:24.1.0") // https://mvnrepository.com/artifact/org.jetbrains/annotations
    api("org.junit.jupiter:junit-jupiter-api:5.10.2") // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    api("org.mockito:mockito-core:5.12.0") // https://mvnrepository.com/artifact/org.mockito/mockito-core
    api("org.mockito:mockito-junit-jupiter:5.12.0") // https://mvnrepository.com/artifact/org.mockito/mockito-junit-jupiter
    api("org.slf4j:slf4j-api:2.0.13") // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    api("org.slf4j:slf4j-simple:2.0.13") // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    api(libs.grpc.all)
    api(libs.sqlite.jdbc)
    api(platform(libs.protobuf.bom))
}
