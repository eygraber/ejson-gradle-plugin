package com.eygraber.ejson.gradle

open class EjsonExtension {
    var loggingEnabled: () -> Boolean = { false }
    var removePublicKey: () -> Boolean = { true }
    var ejsonPath: () -> String? = { null }
    var ejsonKeyDir: () -> String? = { null }
    var ignoreVariantErrorsPredicate: ((variant: String) -> Boolean) = { false }
    var onSecretsDecrypted: (globalSecrets: MutableMap<String, Any>, variantSecrets: Map<String, MutableMap<String, Any>>) -> Unit =
        { _, _ -> }
}