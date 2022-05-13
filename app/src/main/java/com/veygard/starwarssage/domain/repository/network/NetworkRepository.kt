package com.veygard.starwarssage.domain.repository.network

import com.veygard.starwarssage.domain.response.RequestResult

interface NetworkRepository {
    suspend fun getMovies(): RequestResult

    suspend fun downloadPlanets()
    suspend fun downloadPeople()

    suspend fun getPerson(index:Int) : RequestResult
    suspend fun getPlanet(index:Int) : RequestResult
}