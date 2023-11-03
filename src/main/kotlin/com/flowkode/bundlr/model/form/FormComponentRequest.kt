package com.flowkode.bundlr.model.form

data class FormComponentRequest(
    val id: String,
    val value: String

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FormComponentRequest

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
