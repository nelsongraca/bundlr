package com.flowkode.bundlr.error

class ProjectNotFoundException : BundlrException {
    constructor() : super()
    constructor(message: String) : super(message)
}
