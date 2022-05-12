package com.veygard.starwarssage.data.network.response

import com.veygard.starwarssage.data.entities.PlanetEntity

data class Planet(
    val climate: String? = null,
    val created: String? = null,
    val diameter: String? = null,
    val edited: String? = null,
    val films: List<String>? = null,
    val gravity: String? = null,
    val name: String? = null,
    val orbital_period: String? = null,
    val population: String? = null,
    val residents: List<String>? = null,
    val rotation_period: String? = null,
    val surface_water: String? = null,
    val terrain: String? = null,
    val url: String? = null
)

fun Planet.toEntity() = PlanetEntity(
    climate = climate,
    created = created,
    diameter = diameter,
    edited = edited,
    films = films,
    gravity = gravity,
    name = name,
    orbital_period = orbital_period,
    population = population,
    residents = residents,
    rotation_period = rotation_period,
    surface_water = surface_water,
    terrain = terrain,
    url = url ?: ""
)