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
    compileOnly("com.google.code.gson:gson:2.11.0")
    compileOnly("com.google.guava:guava:33.3.1-jre")
    compileOnly("org.jetbrains:annotations:26.0.1")
    compileOnly("org.slf4j:slf4j-api:2.0.16")
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
