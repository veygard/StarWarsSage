package com.veygard.starwarssage.domain.model

import com.veygard.starwarssage.data.entities.PersonEntity

data class Person(
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
    val vehicles: List<String>? = null,
    var avatarUrl:String?= null
)

fun Person.toEntity() = PersonEntity(
    birth_year = this.birth_year,
    created = this.created,
    edited = this.edited,
    eye_color = this.eye_color,
    films = this.films,
    gender = this.gender,
    hair_color = this.hair_color,
    height = this.height,
    homeworld = this.homeworld,
    mass = this.mass,
    name = this.name,
    skin_color = this.skin_color,
    species = this.species,
    starships = this.starships,
    url = this.url ?: "",
    vehicles = this.vehicles,
    avatarUrl= avatarUrl
)