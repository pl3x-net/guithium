plugins {
    alias(libs.plugins.fabric.loom)
}

dependencies {
    minecraft(libs.minecraft.get())
    mappings(loom.officialMojangMappings())

    modCompileOnly(libs.fabric.loader.get())
    modCompileOnly(libs.fabric.api.get())

    compileOnly(libs.adventure.fabric) {
        // TODO: temporary - kyori compiled ansi against jdk22
        //       which causes the remapJar task to fail :/
        exclude("net.kyori", "ansi")
    }

    // this escapes >= in fabric.mod.json to \u003e\u003d
    // but its the only sane way to include adventure jar
    // without also pulling in all the other b.s.
    include(libs.adventure.fabric)
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    filteringCharset = Charsets.UTF_8.name()
    with(copySpec {
        from("src/main/resources/fabric.mod.json") {
            mapOf(
                "version" to "$version",
                "minecraft" to libs.versions.minecraft.get(),
                "fabricloader" to libs.versions.fabricLoader.get()
            ).forEach { k, v -> filter { it.replace("\${$k}", v) } }
        }
    })
}
