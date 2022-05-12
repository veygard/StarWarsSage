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
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanet(planetEntity: PlanetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(personEntity: PersonEntity)

    @Query("SELECT * FROM movie_table ORDER BY episode_id DESC")
    fun getAllMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM people_table where url = :url")
    fun getPerson(url: String): LiveData<PersonEntity>

    @Query("SELECT * FROM planet_table where url = :url")
    fun getPlanet(url: String): LiveData<PersonEntity>

}