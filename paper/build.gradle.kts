plugins {
    alias(libs.plugins.paperweight)
    alias(libs.plugins.shadowjar)
}

dependencies {
    implementation(project(":api"))

    paperweight.paperDevBundle("${libs.versions.minecraft.get()}-R0.1-SNAPSHOT")

    implementation(libs.bstats.get())
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveClassifier.set("")
        arrayOf(
            "org.bstats",
        ).forEach { it -> relocate(it, "libs.$it") }
    }
}
