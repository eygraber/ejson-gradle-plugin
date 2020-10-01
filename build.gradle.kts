buildscript {
    repositories {
        mavenLocal()
        google()
        jcenter()
    }

    dependencies {
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
        classpath("com.eygraber:ejson-gradle-plugin:${Versions.ArtifactVersion}")
    }
}

plugins {
    kotlin("jvm") version "1.4.10" apply false
}
