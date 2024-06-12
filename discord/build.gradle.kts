plugins {
    id("dev.jacksonbailey.wheel.java-application-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
    id("io.spring.dependency-management") version "1.1.5"
    id("org.springframework.boot") version "3.3.0"
}

// Even though this is just for one project, it might be nice to extract it out somewhere later
dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.0")
    }
    dependencies {
        dependency("org.jetbrains:annotations:24.1.0")
        dependency("net.dv8tion:JDA:5.0.0-beta.24")
    }
}

dependencies {
    compileOnly("org.jetbrains:annotations")
    implementation("net.dv8tion:JDA") { exclude(module = "opus-java") }
    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework:spring-context")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
