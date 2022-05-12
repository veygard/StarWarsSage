package com.veygard.starwarssage.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.veygard.starwarssage.data.entities.MovieEntity
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [MovieEntity::class], version = 1)
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