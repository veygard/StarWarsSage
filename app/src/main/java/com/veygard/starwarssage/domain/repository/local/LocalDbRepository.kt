package com.veygard.starwarssage.domain.repository.local

import com.veygard.starwarssage.data.network.response.Movie

interface LocalDbRepository {
    suspend fun insertMovies(movieList: List<Movie>)

    suspend fun insertPlanet()

    suspend fun insertPerson()

    suspend fun getAllMovies() : List<Movie>

    fun getPerson()

    fun getPlanet()
}