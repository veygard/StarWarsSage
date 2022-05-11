package com.veygard.starwarssage.data.network.response

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