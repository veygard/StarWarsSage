package com.veygard.starwarssage.domain.use_case.local

import com.veygard.starwarssage.domain.repository.local.LocalDbRepository


class GetLocalMoviesUseCase(private val localDbRepository: LocalDbRepository) {
    suspend fun start() = localDbRepository.getAllMovies()
}