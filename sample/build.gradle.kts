import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.31"
    id("com.eygraber.ejson")

}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

ejson {
    onSecretsDecrypted = { globalSecrets, _ ->
        println("!!!!!!!!!!!!!! ${globalSecrets}")
    }
}