import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")

    id("java-gradle-plugin")
}

gradlePlugin {
    plugins {
        register("ejson") {
            id = "com.eygraber.ejson"
            implementationClass = "com.eygraber.ejson.gradle.EjsonPlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
    jcenter()
}

dependencies {
    compileOnly(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.android.tools.build:gradle:4.1.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

apply(from = File(rootDir, "publishing.gradle"))
