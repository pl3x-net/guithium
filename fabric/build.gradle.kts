plugins {
    alias(libs.plugins.fabric.loom)
}

dependencies {
    minecraft(libs.minecraft.get())
    mappings(loom.officialMojangMappings())

    modCompileOnly(libs.fabric.loader.get())
    modCompileOnly(libs.fabric.api.get())

    compileOnly(libs.adventure.fabric) {
        // Temporary: kyori compiled ansi against jdk22
        //       which causes the remapJar task to fail :/
        exclude("net.kyori", "ansi")
    }

    // this escapes >= in fabric.mod.json to \u003e\u003d
    // but its the only sane way to include adventure jar
    // without also pulling in all the other b.s.
    include(libs.adventure.fabric)
}

tasks.processResources {
    filteringCharset = Charsets.UTF_8.name()

    // work around IDEA-296490
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    with(copySpec {
        from("src/main/resources/fabric.mod.json") {
            expand(
                "version" to "${project.version}",
                "minecraft" to libs.versions.minecraft.get(),
                "fabricloader" to libs.versions.fabricLoader.get(),
                "description" to "${project.description}",
                "website" to "${ext["website"]}"
            )
        }
    })
}
