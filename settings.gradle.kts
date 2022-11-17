pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://maven.fabricmc.net/")
    }
}

rootProject.name = "Guithium"

include("api")
include("bukkit")
include("fabric")
