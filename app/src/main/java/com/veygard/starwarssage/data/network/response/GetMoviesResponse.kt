package com.veygard.starwarssage.data.network.response

import com.veygard.starwarssage.domain.model.Movie

data class GetMoviesResponse(
    val count: Int? = null,
    val next: Boolean? = null,
    val previous: Boolean? = null,
    val results: List<Movie>? = null
)