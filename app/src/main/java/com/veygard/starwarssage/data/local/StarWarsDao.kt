package com.veygard.starwarssage.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.veygard.starwarssage.data.entities.MovieEntity
import com.veygard.starwarssage.data.entities.PersonEntity
import com.veygard.starwarssage.data.entities.PlanetEntity

@Dao
interface StarWarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDao(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanetDao(planetEntity: PlanetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonDao(personEntity: PersonEntity)

    @Query("SELECT * FROM movie_table ORDER BY episode_id DESC")
    suspend fun getAllMoviesDao(): List<MovieEntity>

    @Query("SELECT * FROM movie_table where url = :url")
    suspend fun getMovieDao(url: String): MovieEntity?

    @Query("SELECT * FROM people_table where url = :url")
    fun getPersonDao(url: String): PersonEntity

    @Query("SELECT * FROM planet_table where url = :url")
    fun getPlanetDao(url: String): PersonEntity

}