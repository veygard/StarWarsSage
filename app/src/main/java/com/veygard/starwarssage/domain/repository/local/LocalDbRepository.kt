package com.veygard.starwarssage.domain.repository.local

import com.veygard.starwarssage.domain.model.Movie
import com.veygard.starwarssage.domain.model.Person
import com.veygard.starwarssage.domain.model.Planet


interface LocalDbRepository {
    suspend fun insertMovies(movieList: List<Movie>)

    suspend fun insertPlanet(planet: Planet)

    suspend fun insertPerson(person: Person)

    suspend fun getAllMovies() : List<Movie>

    suspend fun getMovie(url:String) : Movie?

    suspend fun getPerson(url:String) : Person?

    suspend fun getPlanet(url:String): Planet?
}