package com.flowkode.bundlr.error

open class BundlrException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}
