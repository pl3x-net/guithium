plugins {
    `java-library`
}

java {
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains:annotations:23.0.0")
}

base {
    archivesName = "${rootProject.name}-${project.name}"
}

tasks {
    javadoc {
        options.encoding = "UTF-8"
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        title = "${rootProject.name}-${rootProject.version} API"
    }
}
