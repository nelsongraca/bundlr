package com.flowkode.bundlr.model.bom

data class BomPart(
    val name: String,
    val amount: Int = 1,
    val optional: Boolean = false,
)