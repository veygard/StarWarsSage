package com.veygard.starwarssage.domain.response

import com.veygard.starwarssage.data.network.response.GetMoviesResponse
import com.veygard.starwarssage.data.network.response.Person
import com.veygard.starwarssage.data.network.response.Planet


sealed class RequestResult(open val error: String? = null, open val response: ApiResponseType? = null) {
    data class Success(override val response: ApiResponseType) : RequestResult(response = response)
    data class ConnectionError(override val error: String? = null) : RequestResult(error = error)
    data class ServerError(override val error: String? = null) : RequestResult(error = error)
    data class EnqueueError(override val error: String? = null) : RequestResult(error = error)
}

sealed class ApiResponseType{
    data class GetMovies(val getMoviesResponse: GetMoviesResponse): ApiResponseType()
    data class GetPerson(val person: Person): ApiResponseType()
    data class GetPlanet(val planet: Planet): ApiResponseType()
}
