package com.flowkode.bundlr.model

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.annotation.ObjectIdGenerators

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
data class Part(
    val id: String,
    val name: String,
    val file: String? = null,
    val optional: Boolean = false,
    @JsonSetter(nulls = Nulls.SKIP)
    val dependencies: List<Dependency> = emptyList(),
    @JsonSetter(nulls = Nulls.SKIP)
    val links: List<String> = emptyList()
) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Part

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Part(id='$id', name='$name', file=$file, optional=$optional, dependencies=$dependencies, links=$links)"
    }
}