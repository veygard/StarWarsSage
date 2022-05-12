package com.veygard.starwarssage.data.network.response

import com.veygard.starwarssage.data.entities.MovieEntity

data class Movie(
    val characters: List<String>? = null,
    val created: String? = null,
    val director: String? = null,
    val edited: String? = null,
    val episode_id: Int? = null,
    val opening_crawl: String? = null,
    val planets: List<String>? = null,
    val producer: String? = null,
    val release_date: String? = null,
    val species: List<String>? = null,
    val starships: List<String>? = null,
    val title: String? = null,
    val url: String? = null,
    val vehicles: List<String>? = null
)

fun Movie.toEntity() = MovieEntity(
    characters = this.characters,
    created = this.created,
    director = this.director,
    edited = this.edited,
    episode_id = this.episode_id,
    opening_crawl = this.opening_crawl,
    planets = this.planets,
    producer = this.producer,
    release_date = this.release_date,
    species = this.species,
    starships = this.starships,
    title = this.title,
    url = this.url ?: "",
    vehicles = this.vehicles
)

fun List<Movie>.toEntityList() = this.map { it.toEntity() }