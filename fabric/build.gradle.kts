plugins {
    id("guithium.common-java")
    id("fabric-loom") version "1.0-SNAPSHOT"
}

group = "${rootProject.group}.fabric"
description = "Guithium Fabric Mod"

repositories {
    maven("https://maven.fabricmc.net/")
    maven("https://maven.terraformersmc.com/")
}

dependencies {
    implementation(project(":api"))
    minecraft("com.mojang:minecraft:${project.extra["minecraft_version"]}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${project.extra["loader_version"]}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.extra["fabric_version"]}")
    modImplementation("com.terraformersmc:modmenu:${project.extra["modmenu_version"]}")
    modImplementation(include("net.kyori:adventure-platform-fabric:5.4.0")!!)
    modImplementation(include("net.kyori:adventure-text-serializer-gson:4.11.0")!!)
}

tasks {
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
    @Suppress("UnstableApiUsage")
    mixin.defaultRefmapName.set("guithium.refmap.json")
    accessWidenerPath.set(file("src/main/resources/guithium.accesswidener"))
    runConfigs.configureEach {
        ideConfigGenerated(true)
    }
}
