plugins {
    `java-library`
    alias(libs.plugins.minotaur)
    alias(libs.plugins.indra.git)
}

val mergedJar by configurations.creating<Configuration> {
    isCanBeResolved = true
    isCanBeConsumed = false
    isVisible = false
}

dependencies {
    mergedJar(project(":api"))
    mergedJar(project(":fabric"))
    mergedJar(project(":paper"))
}

allprojects {
    apply(plugin = "java-library")

    version = rootProject.libs.versions.guithium.get()

    java {
        toolchain.languageVersion = JavaLanguageVersion.of(21)
    }

    tasks.compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release = 21
    }
}

subprojects {
    base {
        archivesName = "${rootProject.name}-${project.name}"
    }

    dependencies {
        if (name != "api") {
            // api can't include itself
            compileOnly(project(":api"))
        }

        if (name != "paper") {
            // paper already natively ship with adventure
            compileOnly(rootProject.libs.adventure.api.get())
            compileOnly(rootProject.libs.adventure.gson.get())
        }

        testImplementation(rootProject.libs.junit.get())
        testImplementation(rootProject.libs.asm.get())
        testRuntimeOnly(rootProject.libs.junitPlatform.get())
    }

    configurations {
        // ensure junit tests get the same dependencies as compileOnly
        testImplementation.get().extendsFrom(compileOnly.get())
    }

    tasks.test {
        useJUnitPlatform()

        // we want to see system.out from tests
        testLogging.showStandardStreams = true
    }
}

// this must be after subprojects block
tasks {
    withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
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
}

modrinth {
    autoAddDependsOn = false
    token = System.getenv("MODRINTH_TOKEN")
    projectId = rootProject.name
    versionName = "$version"
    versionNumber = "$version"
    versionType = "alpha"
    uploadFile = tasks.jar.get().archiveFile.get()
    gameVersions.addAll(listOf(libs.versions.minecraft.get()))
    loaders.addAll(listOf("paper", "purpur", "fabric"))
    changelog = System.getenv("COMMIT_MESSAGE")
    dependencies {
        required.project("fabric-api")
    }
}
