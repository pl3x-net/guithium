plugins {
    java
    `java-library`
    `maven-publish`
}

base.archivesName.set("${rootProject.name}-${project.name}")
version = rootProject.version

java {
    withSourcesJar()
    withJavadocJar()
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
}

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
            artifactId = "${rootProject.name}-${project.name}"
            version = "${rootProject.version}"
            from(components["java"])
        }
    }
}
