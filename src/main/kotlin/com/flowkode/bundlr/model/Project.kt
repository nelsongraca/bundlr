package com.flowkode.bundlr.model

data class Project(
        val config: Config,
        val parts:Set<Part>,
        val components:List<Component>


) {
        override fun toString(): String {
                return "Project(config=$config, parts=$parts, components=$components)"
        }
}
