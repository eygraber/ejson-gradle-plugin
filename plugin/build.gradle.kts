
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.31"
    id("java-gradle-plugin")

    id("com.github.dcendents.android-maven")
    id("com.jfrog.bintray")
    id("org.jetbrains.dokka")
}

gradlePlugin {
    plugins {
        register("ejson") {
            id = "com.eygraber.ejson"
            implementationClass = "com.eygraber.ejson.gradle.EjsonPlugin"
        }
    }
}

group = "com.eygraber"
version = Versions.ArtifactVersion

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

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

repositories {
    google()
    jcenter()
}

dependencies {
    compileOnly(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.android.tools.build:gradle:3.4.0")
}

defaultTasks("dokka")

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

apply {
    from(project.file("publishing.gradle"))
}