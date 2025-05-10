plugins {
    `java-library`
    alias(libs.plugins.fabric.loom)
}

version = rootProject.version

base {
    archivesName = "${rootProject.name}-${project.name}"
}

repositories {
    maven("https://maven.fabricmc.net/")
}

dependencies {
    compileOnly(project(":api"))
    minecraft("com.mojang:minecraft:${libs.versions.minecraft.get()}")
    mappings(loom.officialMojangMappings())
    modCompileOnly("net.fabricmc:fabric-loader:${libs.versions.fabricLoader.get()}")
    modCompileOnly("net.fabricmc.fabric-api:fabric-api:${libs.versions.fabricApi.get()}")
}

tasks {
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        filesMatching("fabric.mod.json") {
            expand(
                "version" to version,
                "minecraft" to libs.versions.minecraft.get(),
                "fabricloader" to libs.versions.fabricLoader.get(),
            )
        }
    }
}
