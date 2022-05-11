package com.veygard.starwarssage.data.network.response

data class People(
    val birth_year: String? = null,
    val created: String? = null,
    val edited: String? = null,
    val eye_color: String? = null,
    val films: List<String>? = null,
    val gender: String? = null,
    val hair_color: String? = null,
    val height: String? = null,
    val homeworld: String? = null,
    val mass: String? = null,
    val name: String? = null,
    val skin_color: String? = null,
    val species: List<String>? = null,
    val starships: List<String>? = null,
    val url: String? = null,
    val vehicles: List<String>? = null
)