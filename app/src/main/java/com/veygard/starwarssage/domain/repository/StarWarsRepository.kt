package com.veygard.starwarssage.domain.repository

import com.veygard.starwarssage.domain.response.RequestResult

interface StarWarsRepository {
    suspend fun getMovies(): RequestResult
    suspend fun getPerson(index:Int) : RequestResult
    suspend fun getPlanet(index:Int) : RequestResult
}