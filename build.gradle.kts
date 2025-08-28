plugins {
    kotlin("jvm") version "2.0.21"
    id("com.typewritermc.module-plugin") version "1.1.3"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.typewritermc.com/beta")
    maven("https://mvn.lumine.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.typewritermc:engine-paper:0.8.0")
    compileOnly("com.github.retrooper.packetevents:api:2.2.1")
    compileOnly("io.papermc.paper:paper-api:1.21.5-R0.1-SNAPSHOT")
}

typewriter {
    namespace = "gamesofeducation"

    extension {
        name = "GazeFilter"
        shortDescription = "Audience filters for Typewriter entities"
        description = """
            Adds audience filters that check if a player is looking at a Typewriter entity.
            Uses entity definitions and per-player packet-based positions to determine visibility.
        """.trimIndent()
        engineVersion = "0.8.0"
        channel = com.typewritermc.moduleplugin.ReleaseChannel.BETA

        paper()
    }
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "21"
}
