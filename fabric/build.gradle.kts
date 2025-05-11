plugins {
    alias(libs.plugins.fabric.loom)
}

dependencies {
    minecraft(libs.minecraft.get())
    mappings(loom.officialMojangMappings())

    modCompileOnly(libs.fabric.loader.get())
    modCompileOnly(libs.fabric.api.get())
}

tasks.processResources {
    filteringCharset = Charsets.UTF_8.name()
    filesMatching("fabric.mod.json") {
        expand(
            "version" to "$version",
            "minecraft" to libs.versions.minecraft.get(),
            "fabricloader" to libs.versions.fabricLoader.get(),
        )
    }
}
