package com.veygard.starwarssage.domain.repository.local

import com.veygard.starwarssage.data.local.StarWarsDao
import javax.inject.Inject

class LocalDbRepositoryImpl @Inject constructor(private val starWarsDatabase: StarWarsDao) : LocalDbRepository {

    override suspend fun insertMovies() {

    }

    override suspend fun insertPlanet() {

    }

    override suspend fun insertPerson() {

    }

    override fun getAllMovies() {

    }

    override fun getPerson() {

    }

    override fun getPlanet() {

    }
}