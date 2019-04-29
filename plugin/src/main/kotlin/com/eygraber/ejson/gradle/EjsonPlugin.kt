package com.eygraber.ejson.gradle

import com.android.build.gradle.BasePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel

class EjsonPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val ejsonExtension = project.extensions.run {
            create("ejson", EjsonExtension::class.java)
        }

        val ejson = Ejson(project, ejsonExtension)

        project.afterEvaluate { evaluatedProject ->
            val globalSecrets = ejson.decrypt(secrets = evaluatedProject.file("secrets.ejson"))

            if(ejsonExtension.isLoggingEnabled) project.logger.log(LogLevel.INFO, "Ejson: Global Secrets - $globalSecrets")

            if(ejsonExtension.removePublicKey) globalSecrets.remove("_public_key")

            val variantSecrets = mutableMapOf<String, MutableMap<String, Any>>()
            evaluatedProject.plugins.withType(BasePlugin::class.java).firstOrNull()?.let { plugin ->
                if(ejsonExtension.isLoggingEnabled) project.logger.log(LogLevel.INFO, "Ejson: android plugin added")
                plugin
                    .extension
                    .sourceSets
                    .names
                    .forEach { name ->
                        if(ejsonExtension.isLoggingEnabled) project.logger.log(LogLevel.INFO, "Ejson: processing android source - $name")

                        val ignoreReleaseErrors = ejsonExtension.ignoreVariantErrorsPredicate(name)

                        ejson.decrypt(
                            secrets = evaluatedProject.file("src/$name/secrets.ejson"),
                            ignoreErrors = ignoreReleaseErrors
                        ).takeIf { it.isNotEmpty() }?.let {
                            if(ejsonExtension.removePublicKey) it.remove("_public_key")
                            variantSecrets[name] = it
                            if(ejsonExtension.isLoggingEnabled) project.logger.log(LogLevel.INFO, "Ejson: $name Secrets - $it")
                        }
                    }
            }

            ejsonExtension.onSecretsDecrypted(globalSecrets, variantSecrets)
        }
    }
}
