package com.veygard.starwarssage.domain.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.veygard.starwarssage.data.entities.MovieEntity
import com.veygard.starwarssage.data.entities.PersonEntity
import com.veygard.starwarssage.data.entities.PlanetEntity

interface LocalDbRepository {
    suspend fun insertMovies()

    suspend fun insertPlanet()

    suspend fun insertPerson()

    fun getAllMovies()

    fun getPerson()

    fun getPlanet()
}