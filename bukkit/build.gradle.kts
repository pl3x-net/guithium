plugins {
    `java-library`
}

version = "${rootProject.version}"

base {
    archivesName = "${rootProject.name}-${project.name}"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(project(":api"))
    compileOnly("io.papermc.paper:paper-api:${libs.versions.minecraft.get()}-R0.1-SNAPSHOT")
}

tasks {
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        filesMatching("plugin.yml") {
            expand(
                "version" to version,
                "minecraft" to libs.versions.minecraft.get()
            )
        }
    }
}
