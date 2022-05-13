package com.veygard.starwarssage.di

import android.app.Application
import androidx.room.Room
import com.veygard.starwarssage.data.local.StarWarsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: StarWarsDatabase.Callback
    ) = Room.databaseBuilder(app, StarWarsDatabase::class.java, "star_wars_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    @Singleton
    fun provideStarWarsDao(starWarsDatabase: StarWarsDatabase) = starWarsDatabase.dao()

}