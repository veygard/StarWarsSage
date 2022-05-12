package com.veygard.starwarssage.data.network.api

import com.veygard.starwarssage.data.network.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsApi {
    @Headers(
        "Content-Type: application/json",
    )

    @GET("films")
    suspend fun getAllFilmsApi(): Response<GetMoviesResponse>

    @GET("people")
    suspend fun getAllPeople(@Query("page") page: Int?): Response<GetPeopleResponse>

    @GET("planets")
    suspend fun getAllPlanets(@Query("page") page: Int?): Response<GetPlanetsResponse>

    @GET("people/{id}")
    suspend fun getPersonApi(@Path("id") index:Int): Response<Person>

    @GET("planets/{id}")
    suspend fun getPlanetApi(@Path("id") index:Int): Response<Planet>
}