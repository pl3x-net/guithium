plugins {
    `java-library`
}

base.archivesName.set("${rootProject.name}-${project.name}")
group = "${rootProject.group}.api"
version = rootProject.version
description = "Guithium API"

java {
    withSourcesJar()
    withJavadocJar()
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("net.kyori:adventure-api:4.11.0")
    compileOnly("net.kyori:adventure-text-serializer-gson:4.11.0")
    compileOnly("com.google.code.gson:gson:2.8.9")
    compileOnly("com.google.guava:guava:31.0.1-jre")
    compileOnly("org.jetbrains:annotations:23.0.0")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
        title = "${rootProject.name}-${project.version} API"
    }
}
