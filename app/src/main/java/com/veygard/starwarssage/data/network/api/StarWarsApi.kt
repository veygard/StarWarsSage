package com.veygard.starwarssage.data.network.api

import com.veygard.starwarssage.data.network.response.GetMoviesResponse
import com.veygard.starwarssage.data.network.response.Person
import com.veygard.starwarssage.data.network.response.Planet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsApi {
    @Headers(
        "Content-Type: application/json",
    )

    @GET("/films")
    suspend fun getAllFilmsApi(): Response<GetMoviesResponse>

    @GET("/people/")
    suspend fun getPersonApi(@Path("id") index:Int): Response<Person>

    @GET("/planet/")
    suspend fun getPlanetApi(@Path("id") index:Int): Response<Planet>
}