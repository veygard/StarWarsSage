package com.veygard.starwarssage.data.network.api

import com.veygard.starwarssage.data.network.response.*
import com.veygard.starwarssage.domain.model.Movie
import com.veygard.starwarssage.domain.model.Person
import com.veygard.starwarssage.domain.model.PersonWithAvatar
import com.veygard.starwarssage.domain.model.Planet
import retrofit2.Response
import retrofit2.http.*

interface StarWarsApi {
    @Headers(
        "Content-Type: application/json",
    )

    @GET("films")
    suspend fun getAllFilmsApi(): Response<GetMoviesResponse>

    @GET("films/{id}")
    suspend fun getMovieApi(@Path("id") index:Int): Response<Movie>

    @GET("people")
    suspend fun getAllPeople(@Query("page") page: Int?): Response<GetPeopleResponse>

    @GET("planets")
    suspend fun getAllPlanets(@Query("page") page: Int?): Response<GetPlanetsResponse>

    @GET("people/{id}")
    suspend fun getPersonApi(@Path("id") index:Int): Response<Person>

    @GET("planets/{id}")
    suspend fun getPlanetApi(@Path("id") index:Int): Response<Planet>

    @GET()
    suspend fun getAvatarUrl(@Url url: String): Response<PersonWithAvatar>

}