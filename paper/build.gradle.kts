plugins {
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.16"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle("${libs.versions.minecraft.get()}-R0.1-SNAPSHOT")
}

tasks.processResources {
    filteringCharset = Charsets.UTF_8.name()
    filesMatching("plugin.yml") {
        expand(
            "version" to "$version",
            "minecraft" to libs.versions.minecraft.get()
        )
    }
}
