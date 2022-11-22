pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://maven.fabricmc.net/")
    }

    @Suppress("UnstableApiUsage")
    includeBuild("build-logic")
}

rootProject.name = "guithium"

include("api")
include("bukkit")
include("fabric")
