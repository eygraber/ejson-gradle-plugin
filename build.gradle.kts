
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:0.9.18")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
    }
}

plugins {
    kotlin("jvm") version "1.3.30"

    id("com.github.dcendents.android-maven") version "2.1"
    id("com.jfrog.bintray") version "1.8.4"
    id("org.jetbrains.dokka") version "0.9.18"
}

group = "com.eygraber"
version = "1.0.0"

tasks.withType<DokkaTask> {
    outputFormat = "html"
    outputDirectory = "$buildDir/docs/kdoc"
    linkMapping {
        dir = "src/main/java"
        url = "https://github.com/eygraber/ejson-gradle-plugin/blob/master/library/src/main/java"
        suffix = "#L"
    }
    jdkVersion = 8
}

defaultTasks("dokka")

repositories {
    google()
    jcenter()

    maven(url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {
    compileOnly(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.11.0")
    implementation("com.android.tools.build:gradle:3.4.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

apply {
    from(rootProject.file("publishing.gradle"))
}
