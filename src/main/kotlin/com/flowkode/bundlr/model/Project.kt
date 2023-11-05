package com.flowkode.bundlr.model

import com.fasterxml.jackson.annotation.JsonClassDescription
import com.fasterxml.jackson.annotation.JsonProperty

@JsonClassDescription("The root of your project")
data class Project(
        @JsonProperty(required = true)
        val config: Config,
        val parts:Set<Part>,
        val components:List<Component>


) {
        override fun toString(): String {
                return "Project(config=$config, parts=$parts, components=$components)"
        }
}
