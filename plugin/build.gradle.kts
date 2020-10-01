import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka") version "1.4.10"

    id("java-gradle-plugin")

    id("com.github.dcendents.android-maven")
    id("com.jfrog.bintray")
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

tasks.dokkaHtml {
    dokkaSourceSets {
        named("main") {
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(
                    URL(
                        "https://github.com/eygraber/ejson-gradle-plugin/blob/master/library/src/main/java"
                    )
                )
                remoteLineSuffix.set("#L")
            }
        }
    }
}

repositories {
    google()
    jcenter()
}

dependencies {
    compileOnly(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.android.tools.build:gradle:4.0.1")
}

defaultTasks("dokka")

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

apply {
    from(project.file("publishing.gradle"))
}
