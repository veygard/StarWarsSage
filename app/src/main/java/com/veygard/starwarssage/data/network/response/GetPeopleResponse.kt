package com.veygard.starwarssage.data.network.response

data class GetPeopleResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Person>
)