plugins {
    `java-library`
}

java {
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.google.code.gson:gson:2.11.0")
    compileOnly("com.google.guava:guava:33.3.1-jre")
    compileOnly("org.jetbrains:annotations:26.0.1")
    compileOnly("org.slf4j:slf4j-api:2.0.16")

    testImplementation("org.junit.jupiter:junit-jupiter:5.12.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

configurations {
    // ensure junit tests get the same dependencies as compileOnly
    testImplementation.get().extendsFrom(compileOnly.get())
}

base {
    archivesName = "${rootProject.name}-${project.name}"
}

tasks {
    javadoc {
        options.encoding = "UTF-8"
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        title = "${rootProject.name}-${rootProject.version} API"
    }

    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
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
