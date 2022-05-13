package com.veygard.starwarssage.domain.repository.local

import com.veygard.starwarssage.data.entities.toDomainList
import com.veygard.starwarssage.data.local.StarWarsDao
import com.veygard.starwarssage.domain.model.*
import javax.inject.Inject

class LocalDbRepositoryImpl @Inject constructor(private val starWarsDatabase: StarWarsDao) : LocalDbRepository {

    override suspend fun insertMovies(movieList: List<Movie>) {
        movieList.toEntityList().forEach {
            starWarsDatabase.insertMovieDao(it)
        }
    }

    override suspend fun insertPlanet(planet: Planet) {
        starWarsDatabase.insertPlanetDao(planet.toEntity())
    }

    override suspend fun insertPerson(person: Person) {
        starWarsDatabase.insertPersonDao(person.toEntity())
    }

    override suspend fun getAllMovies(): List<Movie> {
        return starWarsDatabase.getAllMoviesDao().toDomainList()
    }

    override fun getPerson() {

    }

    override fun getPlanet() {

    }
}