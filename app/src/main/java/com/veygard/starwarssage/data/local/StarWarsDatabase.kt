package com.veygard.starwarssage.data.local

import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.veygard.starwarssage.data.entities.MovieEntity
import com.veygard.starwarssage.data.entities.PersonEntity
import com.veygard.starwarssage.data.entities.PlanetEntity
import com.veygard.starwarssage.util.JsonConverter
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [MovieEntity::class, PersonEntity::class, PlanetEntity::class], version = 1)
@TypeConverters(JsonConverter::class)
abstract class StarWarsDatabase : RoomDatabase() {

    abstract fun dao(): StarWarsDao

    class Callback @Inject constructor(
        private val database: Provider<StarWarsDatabase>,
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().dao()
        }
    }
}