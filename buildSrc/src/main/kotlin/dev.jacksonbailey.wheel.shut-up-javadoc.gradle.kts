plugins {
    java
}

tasks {
    javadoc {
        options {
            (this as CoreJavadocOptions).addBooleanOption("Xdoclint:-missing", true)
        }
    }
}
