plugins {
    alias(libs.plugins.fabric.loom)
}

loom {
    @Suppress("UnstableApiUsage")
    mixin.defaultRefmapName = "guithium.refmap.json"
    accessWidenerPath = file("src/main/resources/guithium.accesswidener")
    runConfigs.configureEach {
        ideConfigGenerated(true)
    }
}

dependencies {
    implementation(project(":api"))

    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api.get())

    modImplementation(libs.adventure.fabric) {
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
