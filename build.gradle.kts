buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.14.2")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.4.20")
        classpath("com.eygraber:ejson-gradle-plugin:${findProperty("VERSION_NAME")}")
    }
}

plugins {
    kotlin("jvm") version "1.4.31" apply false
}
