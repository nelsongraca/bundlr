package com.flowkode.bundlr.config

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "core-config")
interface CoreConfig {
    fun allowedProjects(): Set<AllowedProject>
    interface AllowedProject {
        fun name(): String
        fun config(): String
    }
}