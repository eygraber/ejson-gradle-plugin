rootProject.name = "ejson-gradle"

include(":plugin", ":sample")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}
