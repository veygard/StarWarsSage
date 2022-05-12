package com.veygard.starwarssage.domain.use_case

import com.veygard.starwarssage.domain.repository.local.LocalDbRepository
import com.veygard.starwarssage.domain.repository.network.StarWarsRepository


class GetLocalMoviesUseCase(private val localDbRepository: LocalDbRepository) {
    suspend fun start() = localDbRepository.getAllMovies()
}