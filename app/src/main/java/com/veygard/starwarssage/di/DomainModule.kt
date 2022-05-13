package com.veygard.starwarssage.di

import com.veygard.starwarssage.data.local.StarWarsDao
import com.veygard.starwarssage.data.network.api.StarWarsApi
import com.veygard.starwarssage.domain.repository.local.LocalDbRepository
import com.veygard.starwarssage.domain.repository.local.LocalDbRepositoryImpl
import com.veygard.starwarssage.domain.repository.network.NetworkRepository
import com.veygard.starwarssage.domain.repository.network.NetworkRepositoryImpl
import com.veygard.starwarssage.domain.use_case.local.GetLocalMoviesUseCase
import com.veygard.starwarssage.domain.use_case.local.GetLocalPersonUseCase
import com.veygard.starwarssage.domain.use_case.local.GetLocalPlanetUseCase
import com.veygard.starwarssage.domain.use_case.local.LocalUseCases
import com.veygard.starwarssage.domain.use_case.network.*
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
    fun provideNetworkUseCases(
        networkRepository: NetworkRepository,
        localDbRepository: LocalDbRepository
    ): NetworkUseCases = NetworkUseCases(
        getMoviesUseCase = GetMoviesUseCase(repository = networkRepository),
        getPersonUseCase = GetPersonUseCase(repository = networkRepository),
        getPlanetUseCase = GetPlanetUseCase(repository = networkRepository),
        getPlanetsUseCase = GetPlanetsUseCase(repository = networkRepository),
        getPeopleUseCase = GetPeopleUseCase(repository = networkRepository),
    )

    @Provides
    @Singleton
    fun provideLocalUseCases(
        localDbRepository: LocalDbRepository
    ): LocalUseCases = LocalUseCases(
        getLocalMoviesUseCase = GetLocalMoviesUseCase(localDbRepository),
        getLocalPersonUseCase = GetLocalPersonUseCase(localDbRepository),
        getLocalPlanetUseCase = GetLocalPlanetUseCase(localDbRepository)
    )

    @Provides
    @Singleton
    fun provideLocalDbRepository(
        starWarsDao: StarWarsDao
    ): LocalDbRepository = LocalDbRepositoryImpl(starWarsDao)

}