plugins {
    `java-library`
    id("com.modrinth.minotaur") version "2.+"
}

version = "1.21.5-${System.getenv("GITHUB_RUN_NUMBER") ?: "SNAPSHOT"}"

val mergedJar by configurations.creating<Configuration> {
    isCanBeResolved = true
    isCanBeConsumed = false
    isVisible = false
}

repositories {
    maven("https://maven.fabricmc.net/")
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
        manifest {
            attributes["Implementation-Version"] = version
        }
    }
}

modrinth {
    autoAddDependsOn = false
    token = System.getenv("MODRINTH_TOKEN")
    projectId = "guithium"
    versionName = "$version"
    versionNumber = "$version"
    versionType = "alpha"
    uploadFile = rootProject.layout.buildDirectory.file("libs/${rootProject.name}-$version.jar").get()
    gameVersions.addAll(listOf("1.21.5"))
    loaders.addAll(listOf("paper", "purpur", "fabric"))
    changelog = System.getenv("COMMIT_MESSAGE")
}
