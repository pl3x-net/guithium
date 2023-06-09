plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

base.archivesName.set("${rootProject.name}-${project.name}")
group = "${rootProject.group}.plugin"
version = rootProject.version
description = "${rootProject.name} Bukkit Plugin"

java {
    withSourcesJar()
    withJavadocJar()
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))
    compileOnly("io.papermc.paper:paper-api:${project.extra["minecraft_version"]}-R0.1-SNAPSHOT")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        filesMatching("plugin.yml") {
            expand(
                "name" to rootProject.name,
                "group" to project.group,
                "version" to project.version,
                "api_version" to project.extra["bukkit_api_version"],
                "description" to project.description
            )
        }
    }
}
