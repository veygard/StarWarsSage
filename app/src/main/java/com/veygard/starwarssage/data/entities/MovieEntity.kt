package com.veygard.starwarssage.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_table")
@Parcelize
data class MovieEntity(
    val characters: List<String>,
    val created: String,
    val director: String,
    val edited: String,
    @PrimaryKey(autoGenerate = false)val episode_id: Int,
    val opening_crawl: String,
    val planets: List<String>,
    val producer: String,
    val release_date: String,
    val species: List<String>,
    val starships: List<String>,
    val title: String,
    val url: String,
    val vehicles: List<String>
): Parcelable