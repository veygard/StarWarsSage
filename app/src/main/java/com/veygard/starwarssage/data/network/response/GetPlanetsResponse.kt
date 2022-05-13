package com.veygard.starwarssage.data.network.response

import com.veygard.starwarssage.domain.model.Planet

data class GetPlanetsResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Planet>
)