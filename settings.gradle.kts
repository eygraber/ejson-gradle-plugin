rootProject.name = "ejson-gradle"

include(":plugin")
//include(":sample")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}
