plugins {
    `java-library`
    id("fabric-loom") version "1.0-SNAPSHOT"
}

base.archivesName.set("${rootProject.name}-${project.name}")
group = "${rootProject.group}.fabric"
version = rootProject.version
description = "Guithium Fabric Mod"

java {
    withSourcesJar()
    withJavadocJar()
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    maven("https://maven.fabricmc.net/")
    maven("https://maven.terraformersmc.com/")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
        mavenContent { snapshotsOnly() }
    }
    mavenCentral()
}

dependencies {
    implementation(project(":api"))
    minecraft("com.mojang:minecraft:${project.extra["minecraft_version"]}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${project.extra["fabric_loader_version"]}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.extra["fabric_api_version"]}")
    modImplementation("com.terraformersmc:modmenu:${project.extra["modmenu_version"]}")
    modImplementation(include("net.kyori:adventure-platform-fabric:5.8.0")!!)
    modImplementation(include("net.kyori:adventure-text-serializer-gson:4.13.0")!!)
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
    @Suppress("UnstableApiUsage")
    mixin.defaultRefmapName.set("guithium.refmap.json")
    accessWidenerPath.set(file("src/main/resources/guithium.accesswidener"))
    runConfigs.configureEach {
        ideConfigGenerated(true)
    }
}
