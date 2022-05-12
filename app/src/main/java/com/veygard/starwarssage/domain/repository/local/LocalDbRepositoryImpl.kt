package com.veygard.starwarssage.domain.repository.local

import com.veygard.starwarssage.data.entities.toDomainList
import com.veygard.starwarssage.data.local.StarWarsDao
import com.veygard.starwarssage.data.network.response.Movie
import com.veygard.starwarssage.data.network.response.toEntityList
import javax.inject.Inject

class LocalDbRepositoryImpl @Inject constructor(private val starWarsDatabase: StarWarsDao) : LocalDbRepository {

    override suspend fun insertMovies(movieList: List<Movie>) {
        movieList.toEntityList().forEach {
            starWarsDatabase.insertMovieDao(it)
        }
    }

    override suspend fun insertPlanet() {

    }

    override suspend fun insertPerson() {

    }

    override suspend fun getAllMovies(): List<Movie> {
        val l = starWarsDatabase.getAllMoviesDao().toDomainList() ?: emptyList()
        val e = 5
        return l
    }

    override fun getPerson() {

    }

    override fun getPlanet() {

    }
}