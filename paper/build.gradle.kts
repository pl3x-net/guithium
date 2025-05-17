plugins {
    alias(libs.plugins.paperweight)
    alias(libs.plugins.shadowjar)
}

dependencies {
    paperweight.paperDevBundle("${libs.versions.minecraft.get()}-R0.1-SNAPSHOT")

    implementation(libs.bstats.get())
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        archiveClassifier.set("")
        arrayOf(
            "org.bstats",
        ).forEach { it -> relocate(it, "libs.$it") }
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()

        inputs.properties(
            "version" to "${project.version}",
            "minecraft" to libs.versions.minecraft.get(),
            "description" to "${project.description}",
            "website" to "${ext["website"]}"
        )

        // work around IDEA-296490
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        with(copySpec {
            from("src/main/resources/plugin.yml") {
                expand(inputs.properties)
            }
        })
    }
}
