package com.eygraber.ejson.gradle

import com.android.build.gradle.BasePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

private const val EXTENSION = "ejson"

const val KEY_GLOBAL_SECRETS = "global"

class EjsonPlugin : Plugin<Project> {
//    private val preBuildRegex = Regex("pre.*Build")
//    private val Task.isPreBuildTask get() = name.matches(preBuildRegex)
//
//    private fun Task.extractVariantFromPreBuildTask() = name.substring(3, name.indexOf("Build"))
//
//    private fun String.extractFlavorFromVariant() =
//        "${this[0].toLowerCase()}${substring(1).takeWhile { it.isLowerCase() }}"

    override fun apply(project: Project) {
        val ejsonExtension = project.extensions.run {
            create(EXTENSION, EjsonExtension::class.java)
        }

        val decrypted = HashMap<String, Map<String, Any>>()

        val ejson = Ejson(project, ejsonExtension)

        project.afterEvaluate { evaluatedProject ->
            decrypted[KEY_GLOBAL_SECRETS] = ejson.decrypt(secrets = project.file("secrets.ejson"))

            evaluatedProject.plugins.withType(BasePlugin::class.java).whenPluginAdded { plugin ->
                plugin
                    .extension
                    .sourceSets
                    .names
                    .forEach { name ->
                        val ignoreReleaseErrors =
                            ejsonExtension.ignoreMissingReleaseKeys && name.contains("release", ignoreCase = true)

                        decrypted[name] = ejson.decrypt(
                            secrets = evaluatedProject.file("src/$name/secrets.ejson"),
                            ignoreErrors = ignoreReleaseErrors
                        )
                    }
            }

            ejsonExtension.callback?.invoke(decrypted)
        }
    }
}

class EjsonExtension {
    var ejsonPath: String? = null
    var ejsonKeyDir: String? = null
    var ignoreMissingReleaseKeys = false
    var callback: ((secrets: Map<String, Map<String, Any>>) -> Unit)? = null
}