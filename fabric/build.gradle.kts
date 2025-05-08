plugins {
    `java-library`
    id("fabric-loom") version "1.10-SNAPSHOT"
}

repositories {
    maven("https://maven.fabricmc.net/")
}

dependencies {
    implementation(project(":api"))
    minecraft("com.mojang:minecraft:1.21.5")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.16.10")
}

base {
    archivesName = "${rootProject.name}-${project.name}"
}

tasks {
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        filesMatching("fabric.mod.json") {
            expand(
                "version" to rootProject.version
            )
        }
    }
}
