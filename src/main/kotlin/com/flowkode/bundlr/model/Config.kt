package com.flowkode.bundlr.model

data class Config(
        val id: String,
        val baseUrl: String

) {
        override fun toString(): String {
                return "Config(id='$id', baseUrl='$baseUrl')"
        }
}