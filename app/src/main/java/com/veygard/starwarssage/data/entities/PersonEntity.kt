package com.veygard.starwarssage.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.veygard.starwarssage.domain.model.Person
import kotlinx.parcelize.Parcelize

@Entity(tableName = "people_table")
@Parcelize
data class PersonEntity(
    val birth_year: String? = null,
    val created: String? = null,
    val edited: String? = null,
    val eye_color: String? = null,
    val films: List<String>? = null,
    val gender: String? = null,
    val hair_color: String? = null,
    val height: String? = null,
    val homeworld: String? = null,
    val mass: String? = null,
    val name: String? = null,
    val skin_color: String? = null,
    val species: List<String>? = null,
    val starships: List<String>? = null,
    @PrimaryKey(autoGenerate = false)val url: String,
    val vehicles: List<String>? = null
): Parcelable

fun PersonEntity.toDomain() = Person(
    birth_year = this.birth_year,
    created = this.created,
    edited = this.edited,
    eye_color = this.eye_color,
    films = this.films,
    gender = this.gender,
    hair_color = this.hair_color,
    height = this.height,
    homeworld = this.homeworld,
    mass = this.mass,
    name = this.name,
    skin_color = this.skin_color,
    species = this.species,
    starships = this.starships,
    url = this.url ?: "",
    vehicles = this.vehicles
)