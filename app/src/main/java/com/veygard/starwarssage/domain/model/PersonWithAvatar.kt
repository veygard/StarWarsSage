package com.veygard.starwarssage.domain.model

data class PersonWithAvatar(
    val affiliations: List<String>,
    val born: Int,
    val eyeColor: String,
    val formerAffiliations: List<Any>,
    val gender: String,
    val height: Double,
    val homeworld: String,
    val id: Int,
    val image: String,
    val mass: Int,
    val name: String,
    val skinColor: String,
    val species: String,
    val wiki: String
)