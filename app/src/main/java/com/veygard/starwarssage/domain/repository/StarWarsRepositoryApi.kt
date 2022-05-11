package com.veygard.starwarssage.domain.repository

import com.veygard.starwarssage.domain.response.RequestResult

class StarWarsRepositoryApi(): StarWarsRepository {

    override suspend fun getMovies(): RequestResult {
        return RequestResult.ConnectionError()
    }

    override suspend fun getPerson(index: Int): RequestResult {
        return RequestResult.ConnectionError()
    }

    override suspend fun getPlanet(index: Int): RequestResult {
        return RequestResult.ConnectionError()
    }
}