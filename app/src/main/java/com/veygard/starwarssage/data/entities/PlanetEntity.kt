package com.veygard.starwarssage.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "people_table")
@Parcelize
data class PlanetEntity(
    val climate: String? = null,
    val created: String? = null,
    val diameter: String? = null,
    val edited: String? = null,
    val films: List<String>? = null,
    val gravity: String? = null,
    val name: String? = null,
    val orbital_period: String? = null,
    val population: String? = null,
    val residents: List<String>? = null,
    val rotation_period: String? = null,
    val surface_water: String? = null,
    val terrain: String? = null,
    @PrimaryKey(autoGenerate = false)val url: String
): Parcelable