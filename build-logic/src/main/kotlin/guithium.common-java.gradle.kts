plugins {
    java
    `java-library`
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
