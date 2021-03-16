buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.14.2")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.4.20")
    }
}

plugins {
    kotlin("jvm") version "1.4.31" apply false
}
