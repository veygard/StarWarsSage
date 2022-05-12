package com.veygard.starwarssage.domain.repository.local

import com.veygard.starwarssage.data.network.response.Movie
import com.veygard.starwarssage.data.network.response.Person
import com.veygard.starwarssage.data.network.response.Planet

interface LocalDbRepository {
    suspend fun insertMovies(movieList: List<Movie>)

    suspend fun insertPlanet(planet: Planet)

    suspend fun insertPerson(person: Person)

    suspend fun getAllMovies() : List<Movie>

    fun getPerson()

    fun getPlanet()
}