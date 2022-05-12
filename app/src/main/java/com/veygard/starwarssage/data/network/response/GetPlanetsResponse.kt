package com.veygard.starwarssage.data.network.response

data class GetPlanetsResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Planet>
)