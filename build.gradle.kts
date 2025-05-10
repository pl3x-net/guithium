plugins {
    `java-library`
    alias(libs.plugins.minotaur)
    alias(libs.plugins.indra.git)
}

version = libs.versions.guithium.get()

val mergedJar by configurations.creating<Configuration> {
    isCanBeResolved = true
    isCanBeConsumed = false
    isVisible = false
}

dependencies {
    mergedJar(project(":api"))
    mergedJar(project(":bukkit"))
    mergedJar(project(":fabric"))
}

tasks.withType<Jar> {
    dependsOn(mergedJar)
    val jars = mergedJar.map { zipTree(it) }
    from(jars)
    manifest {
        attributes["Implementation-Version"] = version
        attributes["Git-Commit"] = indraGit.commit()?.name()
    }
    doFirst {
        jars.forEach { jar ->
            jar.matching { include("META-INF/MANIFEST.MF") }
                .files.forEach { file ->
                    manifest.from(file)
                }
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
    uploadFile = tasks.jar.get().archiveFile.get()
    gameVersions.addAll(listOf(libs.versions.minecraft.get()))
    loaders.addAll(listOf("bukkit", "spigot", "paper", "purpur", "fabric"))
    changelog = System.getenv("COMMIT_MESSAGE")
    dependencies {
        required.project("fabric-api")
    }
}
