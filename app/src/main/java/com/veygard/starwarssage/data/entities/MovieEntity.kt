package com.veygard.starwarssage.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.veygard.starwarssage.domain.model.Movie
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_table")
@Parcelize
data class MovieEntity(
    val characters: List<String> ? = null,
    val created: String ? = null,
    val director: String ? = null,
    val edited: String ? = null,
    val episode_id: Int ? = null,
    val opening_crawl: String ? = null,
    val planets: List<String> ? = null,
    val producer: String ? = null,
    val release_date: String ? = null,
    val species: List<String> ? = null,
    val starships: List<String> ? = null,
    val title: String ? = null,
    @PrimaryKey(autoGenerate = false)val url: String,
    val vehicles: List<String> ? = null,
): Parcelable

fun MovieEntity.toDomain() = Movie(
    characters = this.characters,
    created = this.created,
    director = this.director,
    edited = this.edited,
    episode_id = this.episode_id,
    opening_crawl = this.opening_crawl,
    planets = this.planets,
    producer = this.producer,
    release_date = this.release_date,
    species = this.species,
    starships = this.starships,
    title = this.title,
    url = this.url ?: "",
    vehicles = this.vehicles
)

fun List<MovieEntity>.toDomainList() = this.map { it.toDomain() }