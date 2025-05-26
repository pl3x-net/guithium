plugins {
    id("java")
    alias(libs.plugins.minotaur)
    alias(libs.plugins.indra.git)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

allprojects {
    apply(plugin = "java-library")

    group = "net.pl3x.guithium"
    version = System.getenv("VERSION") ?: "${rootProject.libs.versions.guithium.get()}-SNAPSHOT"
    description = "Allows paper plugins to add GUIs to your fabric client"
    ext["website"] = "https://github.com/pl3x-net/guithium"
}

subprojects {
    base {
        archivesName = "${rootProject.name}-${project.name}"
    }

    dependencies {
        testImplementation(rootProject.libs.junit.get())
        testImplementation(rootProject.libs.asm.get())
        testRuntimeOnly(rootProject.libs.junitPlatform.get())
    }

    configurations {
        // ensure junit tests get the same dependencies as compileOnly
        testImplementation.get().extendsFrom(compileOnly.get())
    }

    tasks {
        test {
            useJUnitPlatform()
            // we want to see system.out from tests
            testLogging.showStandardStreams = true
        }

        processResources {
            filteringCharset = Charsets.UTF_8.name()

            // work around IDEA-296490
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
            with(copySpec {
                include("*plugin.yml", "fabric.mod.json")
                from("src/main/resources") {
                    expand(
                        "version" to "${project.version}",
                        "minecraft" to rootProject.libs.versions.minecraft.get(),
                        "fabricloader" to rootProject.libs.versions.fabricLoader.get(),
                        "description" to "${project.description}",
                        "website" to "${ext["website"]}"
                    )
                }
            })
        }
    }
}

// this must be after subprojects block
tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release = 21
    }

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
