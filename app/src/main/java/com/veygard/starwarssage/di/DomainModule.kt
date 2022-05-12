package com.veygard.starwarssage.di

import com.veygard.starwarssage.data.local.StarWarsDao
import com.veygard.starwarssage.data.network.api.StarWarsApi
import com.veygard.starwarssage.domain.repository.local.LocalDbRepository
import com.veygard.starwarssage.domain.repository.local.LocalDbRepositoryImpl
import com.veygard.starwarssage.domain.repository.network.NetworkRepository
import com.veygard.starwarssage.domain.repository.network.NetworkRepositoryImpl
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
        starWarsApi: StarWarsApi,
        localDbRepository: LocalDbRepository
    ): NetworkRepository = NetworkRepositoryImpl(starWarsApi, localDbRepository)

    @Provides
    @Singleton
    fun provideUseCases(
        networkRepository: NetworkRepository,
        localDbRepository: LocalDbRepository
    ): StarWarsUseCases = StarWarsUseCases(
        getMoviesUseCase = GetMoviesUseCase(repository = networkRepository),
        getPersonUseCase = GetPersonUseCase(repository = networkRepository),
        getPlanetUseCase = GetPlanetUseCase(repository = networkRepository),
        getPlanetsUseCase = GetPlanetsUseCase(repository = networkRepository),
        getPeopleUseCase = GetPeopleUseCase(repository = networkRepository),
        getLocalMoviesUseCase = GetLocalMoviesUseCase(localDbRepository)
    )

    @Provides
    @Singleton
    fun provideLocalDbRepository(
        starWarsDao: StarWarsDao
    ): LocalDbRepository = LocalDbRepositoryImpl(starWarsDao)

}