package com.eygraber.ejson.gradle

import com.android.build.gradle.BasePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class EjsonPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val ejsonExtension = project.extensions.run {
            create("ejson", EjsonExtension::class.java)
        }

        val ejson = Ejson(project, ejsonExtension)

        project.afterEvaluate { evaluatedProject ->
            val globalSecrets = ejson.decrypt(secrets = evaluatedProject.file("secrets.ejson"))

            if(ejsonExtension.removePublicKey) globalSecrets.remove("_public_key")

            val variantSecrets = mutableMapOf<String, MutableMap<String, Any>>()
            evaluatedProject.plugins.withType(BasePlugin::class.java).whenPluginAdded { plugin ->
                plugin
                    .extension
                    .sourceSets
                    .names
                    .forEach { name ->
                        val ignoreReleaseErrors = ejsonExtension.ignoreVariantErrorsPredicate(name)

                        ejson.decrypt(
                            secrets = evaluatedProject.file("src/$name/secrets.ejson"),
                            ignoreErrors = ignoreReleaseErrors
                        ).takeIf { it.isNotEmpty() }?.let {
                            if(ejsonExtension.removePublicKey) it.remove("_public_key")
                            variantSecrets[name] = it
                        }
                    }
            }

            ejsonExtension.onSecretsDecrypted(globalSecrets, variantSecrets)
        }
    }
}
