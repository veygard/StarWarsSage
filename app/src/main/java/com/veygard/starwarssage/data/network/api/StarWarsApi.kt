package com.veygard.starwarssage.data.network.api

import com.veygard.starwarssage.data.network.response.GetMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface StarWarsApi {
    @Headers(
        "Content-Type: application/json",
    )

    @GET("/films")
    suspend fun getAllFilms(): Response<GetMoviesResponse>
}