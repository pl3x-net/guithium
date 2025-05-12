plugins {
    `java-library`
    alias(libs.plugins.minotaur)
    alias(libs.plugins.indra.git)
}

version = libs.versions.guithium.get()

java {
    toolchain.languageVersion = JavaLanguageVersion.of(libs.versions.java.get())
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

subprojects {
    apply(plugin = "java-library")

    project.version = rootProject.version

    base {
        archivesName = "${rootProject.name}-${project.name}"
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        if (name != "api") {
            compileOnly(project(":api"))
        }

        compileOnly(rootProject.libs.gson.get())
        compileOnly(rootProject.libs.guava.get())
        compileOnly(rootProject.libs.annotations.get())
        compileOnly(rootProject.libs.slf4j.get())

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
            testLogging {
                events("passed", "skipped", "failed")
                showStandardStreams = true
            }
        }

        withType<AbstractTestTask> {
            afterSuite(KotlinClosure2({ desc: TestDescriptor, result: TestResult ->
                if (desc.parent == null) {
                    println()
                    println("Test Results: ${result.resultType}")
                    println("       Tests: ${result.testCount}")
                    println("      Passed: ${result.successfulTestCount}")
                    println("      Failed: ${result.failedTestCount}")
                    println("     Skipped: ${result.skippedTestCount}")
                }
            }))
        }
    }
}

// this must be after subprojects block
tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release = libs.versions.java.get().toInt()
    }

    withType<Jar> {
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
