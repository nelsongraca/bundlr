package com.flowkode.bundlr.model.form

data class FormComponent(
    val id: String,
    val name: String,
    val options: List<FormOption>
)
