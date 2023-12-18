package com.flowkode.bundlr.model.bom

data class Supplier(
    val name: String,
    val region: String,
    val ships: String,
    val icon: String?,
    val note: String?
)

data class SourceUrl(
    val supplier: Supplier?,
    val url: String,
    val note: String?
)

data class Author(
    val name: String,
    val url: String,
    val note: String?
)

data class Part(
    val name: String,
    val units: String?,
    val partType: String?,
    val icon: String?,
    val fileUrl: String?,
    val template: String?,
    val imgUrl: String?,
    val note: String?,
    val sources: Map<String,SourceUrl> = HashMap(),
    val author: String?
)

data class Variant(
    val name: String?,
    val attributes: Map<String, String> = HashMap(),
    val parts: Map<String, Float> = HashMap(),
    val author: Author?,
    val note: String?,
    val imgUrl: String?
)

data class Component(
    val name: String,
    val template: String?,
    val compType: String?,
    val attributes: Map<String, String> = HashMap(),
    val variants: Map<String, Variant> = HashMap(),
    val note: String?,
    val imgUrl: String?
)

data class BOM(
    val config: Config,
    val components: Map<String, Map<String, Component>> = HashMap(),
    val parts: Map<String, Map<String, Part>> = HashMap(),
    val authors: Map<String, Author>,
    val suppliers: Map<String, Supplier> = HashMap(),
    val assemblyTypes: List<String> = ArrayList(),
    val templates: Map<String, List<String>> = HashMap()
)

data class Config(val id: String, val baseUrl: String)