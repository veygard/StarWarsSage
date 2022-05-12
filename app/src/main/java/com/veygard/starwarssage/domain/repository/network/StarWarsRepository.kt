package com.veygard.starwarssage.domain.repository.network

import com.veygard.starwarssage.domain.response.RequestResult

interface StarWarsRepository {
    suspend fun getMovies(): RequestResult

    suspend fun getPlanets()
    suspend fun getPeople()

    suspend fun getPerson(index:Int) : RequestResult
    suspend fun getPlanet(index:Int) : RequestResult
}