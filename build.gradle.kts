import org.gradle.process.internal.ExecException
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.util.Properties

val i18n4k_version: String by project
val klaxon_version: String by project
val hoplite_version: String by project
val kookybot_version: String by project
val ktor_version: String by project
val kotlin_version: String by project
val slf4j_version: String by project

val versionPropertiesFile = "${projectDir}/project.properties"

fun String.runCommand(currentWorkingDir: File = file("./")): String {
    val byteOut = ByteArrayOutputStream()
    try {
        project.exec {
            workingDir = currentWorkingDir
            commandLine = this@runCommand.split("\\s".toRegex())
            standardOutput = byteOut
        }
        return String(byteOut.toByteArray()).trim()
    } catch (e: ExecException) {
        return "0000000"
    }
}

fun getRevision(): String {
    return "git rev-parse --short=7 HEAD".runCommand()
}

fun getProperties(file: String, key: String): String {
    val fileInputStream = FileInputStream(file)
    val props = Properties()
    props.load(fileInputStream)
    return props.getProperty(key)
}

fun getVersion(): String {
    return getProperties(versionPropertiesFile, "version")
}

fun getStage(): String {
    return getProperties(versionPropertiesFile, "stage")
}

plugins {
    application
    kotlin("jvm") version "1.7.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("de.comahe.i18n4k") version "0.4.0"
}

group = "top.cubik65536"
version = getVersion() + "-" + getStage() + "+" + getRevision()

i18n4k {
    sourceCodeLocales = listOf("en", "zh_CN")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    val projectProps by registering(WriteProperties::class) {
        outputFile = file("${projectDir}/src/main/resources/version.properties")
        encoding = "UTF-8"
        property("version", getVersion())
        property("stage", getStage())
        property("revision", getRevision())
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        exclude("conf/config.yaml")
        from(projectProps)
    }
}

application {
    mainClass.set("top.cubik65536.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    maven { url = uri("https://libraries.minecraft.net") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Dependencies
    // i18n
    implementation("de.comahe.i18n4k:i18n4k-core-jvm:$i18n4k_version")
    // JSON Parser
    implementation("com.beust:klaxon:$klaxon_version")
    // Config Loader
    implementation("com.sksamuel.hoplite:hoplite-core:$hoplite_version")
    implementation("com.sksamuel.hoplite:hoplite-yaml:$hoplite_version")
    // KOOK SDK
    implementation("com.github.KookyBot:KookyBot:$kookybot_version")
    // Ktor
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-html-builder-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-cio-jvm:$ktor_version")
    // Logging Framework
    implementation("org.slf4j:slf4j-simple:$slf4j_version")

    // Test Dependencies
    // Ktor
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    // Kotlin
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
