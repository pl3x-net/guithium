plugins {
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "${rootProject.group}.api"
version = rootProject.version
description = "ServerGui API"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.google.code.gson:gson:2.8.9")
    compileOnly("com.google.guava:guava:31.0.1-jre")
    compileOnly("org.jetbrains:annotations:23.0.0")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    jar {
        archiveFileName.set("${rootProject.name}-${project.name}-${project.version}.jar")
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "${rootProject.group}"
            artifactId = "servergui-api"
            version = "${rootProject.version}"
            from(components["java"])
        }
    }
}
