package com.flowkode.bundlr.error

class ProjectNotFoundException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}
