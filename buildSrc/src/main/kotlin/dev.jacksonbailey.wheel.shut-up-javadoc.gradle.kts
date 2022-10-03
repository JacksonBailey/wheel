plugins {
    java
}

tasks {
    javadoc {
        options {
            (this as CoreJavadocOptions).addStringOption("Xdoclint:none", "-quiet")
        }
    }
}
