plugins {
    `maven-publish`
    alias(libs.plugins.fix.javadoc)
}

java {
    withSourcesJar()
    withJavadocJar()
}

repositories {
    // not sure what's different about this one,
    // but it won't work from settings.gradle.kts
    mavenCentral()
}

dependencies {
    compileOnly(libs.adventure.api.get())
    compileOnly(libs.adventure.gson.get())

    compileOnly(libs.apache.get())
    compileOnly(libs.gson.get())
    compileOnly(libs.guava.get())
    compileOnly(libs.annotations.get())
    compileOnly(libs.slf4j.get())
}

tasks {
    javadoc {
        val name = rootProject.name.replaceFirstChar { it.uppercase() }
        val stdopts = options as StandardJavadocDocletOptions
        stdopts.encoding = Charsets.UTF_8.name()
        stdopts.overview = "src/main/javadoc/overview.html"
        stdopts.use()
        stdopts.isDocFilesSubDirs = true
        stdopts.windowTitle = "$name $version API Documentation"
        stdopts.docTitle = "<h1>$name $version API</h1>"
        stdopts.header = """<img src="https://raw.githubusercontent.com/BillyGalbreath/Guithium/master/fabric/src/main/resources/assets/guithium/icon.png" style="height:100%">"""
        stdopts.bottom = "Copyright © 2025 William Blake Galbreath"
        stdopts.linkSource(true)
        stdopts.addBooleanOption("html5", true)
        stdopts.links(
            "https://guava.dev/releases/${libs.versions.guava.get()}/api/docs/",
            "https://javadoc.io/doc/org.slf4j/slf4j-api/${libs.versions.slf4j.get()}/",
            "https://javadoc.io/doc/org.jetbrains/annotations/${libs.versions.annotations.get()}/"
        )
    }

    withType<com.jeff_media.fixjavadoc.FixJavadoc> {
        configureEach {
            newLineOnMethodParameters.set(false)
            keepOriginal.set(false)
        }
    }
}

publishing {
    repositories {
        maven {
            name = "reposilite"
            url = uri("https://repo.pl3x.net/public/")
            credentials(PasswordCredentials::class)
            authentication.create<BasicAuthentication>("basic")
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "${project.group}"
            artifactId = "${rootProject.name}-${project.name}"
            version = "${project.version}"
            from(components["java"])
        }
    }
}
