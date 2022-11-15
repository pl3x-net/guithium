plugins {
    `java-library`
    id("fabric-loom") version "1.0-SNAPSHOT"
}

base.archivesName.set("${rootProject.name}-${project.name}")
group = "${rootProject.group}.fabric"
version = rootProject.version
description = "ServerGui API"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven("https://maven.terraformersmc.com/")
}

dependencies {
    implementation(project(":api"))
    minecraft("com.mojang:minecraft:${project.extra["minecraft_version"]}")
    //mappings("net.fabricmc:yarn:${project.extra["yarn_mappings"]}:v2")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${project.extra["loader_version"]}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.extra["fabric_version"]}")
    modImplementation("com.terraformersmc:modmenu:${project.extra["modmenu_version"]}")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        filesMatching("fabric.mod.json") {
            expand(
                "version" to project.version
            )
        }
    }
}

loom {
    runConfigs.configureEach {
        ideConfigGenerated(true)
    }
}
