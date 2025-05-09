plugins {
    `java-library`
    id("com.gradleup.shadow") version "8.3.6"
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
