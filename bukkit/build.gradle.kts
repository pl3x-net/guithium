plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))
    compileOnly("io.papermc.paper:paper-api:1.21.5-R0.1-SNAPSHOT")
}

base {
    archivesName = "${rootProject.name}-${project.name}"
}

tasks {
    jar {
        dependsOn(shadowJar)
    }
    shadowJar {
        archiveClassifier.set("")
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        filesMatching("plugin.yml") {
            expand(
                "version" to rootProject.version
            )
        }
    }
}
