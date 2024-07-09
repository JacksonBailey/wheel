import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.ryandens.javaagent-test") version "0.5.1"
    id("dev.jacksonbailey.wheel.java-application-conventions")
    id("dev.jacksonbailey.wheel.shut-up-javadoc")
    id("io.freefair.lombok") version "8.6"
    id("io.spring.dependency-management") version "1.1.5"
    id("org.panteleyev.jpackageplugin") version "1.6.0"
    id("org.springframework.boot") version "3.3.0"
}

// Even though this is just for one project, it might be nice to extract it out somewhere later
dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.0")
    }
    dependencies {
        dependency("net.dv8tion:JDA:5.0.0-beta.24")
        dependency("org.jetbrains:annotations:24.1.0")
    }
}

lombok {
    version = "1.18.32" // https://mvnrepository.com/artifact/org.projectlombok/lombok
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
    testJavaagent("net.bytebuddy:byte-buddy-agent")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.getByName<BootJar>("bootJar") {
    // TODO Does this mess up publishing somehow?
    this.archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}

// TODO Is there a way to get the "into" of a copy task programmatically?
val jpackageInputDirectory = "${layout.buildDirectory.asFile.get().path}/jpackage/input"

val copyBootJar = task("copyBootJar", Copy::class) {
    // TODO Is dependsOn needed when from is a task?
    dependsOn(tasks.bootJar)
    from(tasks.bootJar).into(jpackageInputDirectory)
}

/*
 * This works! When the DMG is installed and ran, it works. It does not show any console and the
 * icon on the taskbar just jumps non-stop, but this works! I will look into it more later.
 */
tasks.jpackage {
    // Only *really* needs to depend on copyBootJar, but it feels odd to not build everything
    dependsOn(copyBootJar, tasks.build)

    input = jpackageInputDirectory
    destination = "${layout.buildDirectory.asFile.get().path}/distributions"

    mainJar = tasks.bootJar.get().archiveFileName.get()
    // Remember, the *real* main class in the boot jar is the JarLauncher
    mainClass = "org.springframework.boot.loader.launch.JarLauncher"

    arguments = listOf("--spring.profiles.active=live")

    mac {
        // For Bundler Mac DMG Package, valid versions are one to three integers separated by dots.
        // The first number in an app-version cannot be zero or negative. The default for this would
        // be the project's version. Therefore, 0.1.0-SNAPSHOT will not work, so I just set it to 1.
        appVersion = "1"
    }
}
