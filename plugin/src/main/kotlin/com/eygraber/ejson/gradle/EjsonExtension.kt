package com.eygraber.ejson.gradle

open class EjsonExtension {
    var onEjsonFailure: ((variant: String) -> Boolean) = { true }
}