plugins {
    alias(libs.plugins.paperweight)
}

dependencies {
    paperweight.paperDevBundle("${libs.versions.minecraft.get()}-R0.1-SNAPSHOT")
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    filteringCharset = Charsets.UTF_8.name()
    with(copySpec {
        from("src/main/resources/plugin.yml") {
            mapOf(
                "version" to "$version",
                "minecraft" to libs.versions.minecraft.get(),
            ).forEach { k, v -> filter { it.replace("\${$k}", v) } }
        }
    })
}
