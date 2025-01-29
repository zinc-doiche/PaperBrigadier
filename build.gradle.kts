plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14"
    `maven-publish`
}

group = "com.devport"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    withSourcesJar()
//    withJavadocJar()
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["java"])
                groupId = "com.github.zinc-doiche"
                artifactId = "PaperBrigadier"
                version = "1.0"

                pom {
                    name.set("name")
                    description.set("description")
                }
            }
        }
    }
}