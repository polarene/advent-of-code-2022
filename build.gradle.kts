plugins {
    kotlin("jvm") version "1.7.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("test-junit5"))
}

sourceSets {
    test {
        kotlin.srcDir(rootDir.resolve("calendar"))
        resources.srcDir(rootDir.resolve("calendar"))
        resources.exclude("**/*.kt")
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
    register("cleanInputs") {
        doFirst {
            val violations = sourceSets.map(SourceSet::getResources).flatMap { ss ->
                ss.files.filter { it.readText().isNotBlank() }
            }
            violations.forEach {
                it.writeText("")
                logger.warn("Cleaned input file ${it.absolutePath}")
            }
        }
    }
}