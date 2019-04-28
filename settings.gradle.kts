rootProject.name = "ejson-gradle"

pluginManagement {
    repositories {
        jcenter()
        gradlePluginPortal()
    }
    
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlinx-serialization") {
                useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
            }
        }
    }
}

