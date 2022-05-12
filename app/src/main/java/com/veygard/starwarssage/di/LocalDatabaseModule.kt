package com.veygard.starwarssage.di

import android.app.Application
import androidx.room.Room
import com.veygard.starwarssage.data.local.StarWarsDao
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
    ) = Room.databaseBuilder(app, StarWarsDatabase::class.java, "task_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideTaskDao(db: StarWarsDatabase) = db.dao()


}