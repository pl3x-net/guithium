plugins {
    id("guithium.common-java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "${rootProject.group}.plugin"
description = "Guithium Bukkit Plugin"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))
    compileOnly("io.papermc.paper:paper-api:${project.extra["minecraft_version"]}-R0.1-SNAPSHOT")
}

tasks {
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        filesMatching("plugin.yml") {
            expand(
                "name" to rootProject.name,
                "group" to project.group,
                "version" to project.version,
                "description" to project.description
            )
        }
    }
}
