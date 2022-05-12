package com.veygard.starwarssage.di

import com.veygard.starwarssage.data.local.StarWarsDao
import com.veygard.starwarssage.data.local.StarWarsDatabase
import com.veygard.starwarssage.data.network.api.StarWarsApi
import com.veygard.starwarssage.domain.repository.local.LocalDbRepository
import com.veygard.starwarssage.domain.repository.local.LocalDbRepositoryImpl
import com.veygard.starwarssage.domain.repository.network.StarWarsRepository
import com.veygard.starwarssage.domain.repository.network.StarWarsRepositoryImpl
import com.veygard.starwarssage.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@DelicateCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(
        starWarsApi: StarWarsApi
    ): StarWarsRepository = StarWarsRepositoryImpl(starWarsApi)

    @Provides
    @Singleton
    fun provideUseCases(
        starWarsRepository: StarWarsRepository,
    ): StarWarsUseCases = StarWarsUseCases(
        getMoviesUseCase = GetMoviesUseCase(repository = starWarsRepository),
        getPersonUseCase = GetPersonUseCase(repository = starWarsRepository),
        getPlanetUseCase = GetPlanetUseCase(repository = starWarsRepository),
        getPlanetsUseCase = GetPlanetsUseCase(repository = starWarsRepository),
        getPeopleUseCase = GetPeopleUseCase(repository = starWarsRepository)
    )

    @Provides
    @Singleton
    fun provideLocalDbRepository(
        starWarsDao: StarWarsDao
    ): LocalDbRepository = LocalDbRepositoryImpl(starWarsDao)

}