package com.flowkode.bundlr.model

data class Project(
        val config: Config,
        val parts:Set<Part>,
        val components:List<Component>

)
