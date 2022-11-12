plugins {
    `java-library`
}

project.version = "${extra["minecraft_version"]}-${System.getenv("GITHUB_RUN_NUMBER") ?: "SNAPSHOT"}"
project.group = "net.pl3x.servergui";

val mergedJar by configurations.creating<Configuration> {
    isCanBeResolved = true
    isCanBeConsumed = false
    isVisible = false
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven("https://maven.terraformersmc.com/")
}

dependencies {
    mergedJar(project(":api"))
    mergedJar(project(":bukkit"))
    mergedJar(project(":fabric"))
}

tasks {
    jar {
        dependsOn(mergedJar)
        archiveBaseName.set(rootProject.name)
        from({ mergedJar.filter { it.name.endsWith("jar") && it.path.contains(rootDir.path) }.map { zipTree(it) } })
    }
}
