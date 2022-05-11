package com.veygard.starwarssage.di

import com.veygard.starwarssage.data.network.api.StarWarsApi
import com.veygard.starwarssage.domain.repository.StarWarsRepository
import com.veygard.starwarssage.domain.repository.StarWarsRepositoryImpl
import com.veygard.starwarssage.domain.use_case.GetMoviesUseCase
import com.veygard.starwarssage.domain.use_case.GetPersonUseCase
import com.veygard.starwarssage.domain.use_case.GetPlanetUseCase
import com.veygard.starwarssage.domain.use_case.StarWarsUseCases
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
        getPlanetUseCase = GetPlanetUseCase(repository = starWarsRepository)
    )

}