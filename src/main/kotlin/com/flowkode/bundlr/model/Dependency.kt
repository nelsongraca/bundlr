package com.flowkode.bundlr.model

data class Dependency(
    val part: Part,
    val amount: Int = 1

) {
    override fun toString(): String {
        return "Dependency(part=$part, amount=$amount)"
    }
}