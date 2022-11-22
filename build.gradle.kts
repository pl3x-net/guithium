plugins {
    `java-library`
    `maven-publish`
    id("com.modrinth.minotaur") version "2.+"
}

var snapshot = ""
if (System.getenv("GITHUB_RUN_NUMBER") == null) {
    snapshot = "-SNAPSHOT"
}
project.version = "${extra["mod_version"]}${snapshot}"
project.group = "net.pl3x.guithium"

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
        manifest {
            attributes["Implementation-Version"] = "${project.extra["mod_version"]}-${System.getenv("GITHUB_RUN_NUMBER") ?: "SNAPSHOT"}"
        }
    }
    publish {
        enabled = false
    }
    modrinth {
        token.set(System.getenv("MODRINTH_TOKEN"))
        projectId.set("guithium")
        versionName.set("${project.extra["minecraft_version"]} ${project.version}")
        versionNumber.set("${project.version}")
        versionType.set("alpha")
        uploadFile.set(jar(rootProject.name))
        gameVersions.addAll(listOf("${project.extra["minecraft_version"]}"))
        loaders.addAll(listOf("spigot", "paper", "purpur", "fabric", "quilt"))
        changelog.set(System.getenv("COMMIT_MESSAGE"))
    }
}

allprojects {
    if (project.name != rootProject.name) {
        apply(plugin = "java")
        apply(plugin = "maven-publish")
        publishing {
            repositories {
                maven {
                    name = "public"
                    url = uri("https://repo.pl3x.net/public")
                    credentials(PasswordCredentials::class)
                    authentication {
                        create<BasicAuthentication>("basic")
                    }
                }
            }
            publications {
                create<MavenPublication>("maven") {
                    groupId = "${rootProject.group}"
                    artifactId = "guithium-${project.name}"
                    version = "${rootProject.version}"
                    from(components["java"])
                }
            }
        }
    }
}

fun jar(name: String): RegularFile {
    return rootProject.layout.buildDirectory.file("libs/${name}-${project.version}.jar").get()
}
