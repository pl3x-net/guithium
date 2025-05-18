plugins {
    `java-library`
    alias(libs.plugins.minotaur)
    alias(libs.plugins.indra.git)
}

allprojects {
    apply(plugin = "java-library")

    group = "net.pl3x.guithium"
    version = System.getenv("VERSION") ?: "${rootProject.libs.versions.guithium.get()}-SNAPSHOT"
    description = "Allows paper plugins to add GUIs to your fabric client"
    ext["website"] = "https://github.com/BillyGalbreath/Guithium"

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
        subprojects {
            dependsOn(project.tasks.build)
        }

        // build our manifest
        manifest {
            attributes["Implementation-Version"] = version
            attributes["Git-Commit"] = indraGit.commit()?.name()
            attributes["Build"] = System.getenv("GITHUB_RUN_NUMBER") ?: -1
        }

        // get subproject's built jars
        val jars = subprojects.map { zipTree(it.tasks.jar.get().archiveFile.get().asFile) }

        // merge them into main jar (except their manifests)
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(jars) {
            exclude("META-INF/MANIFEST.MF")
        }

        // put behind an action because files don't exist at configuration time
        doFirst {
            // merge all subproject's manifests into main manifest
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
