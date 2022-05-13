package com.veygard.starwarssage.domain.use_case.network

import com.veygard.starwarssage.domain.repository.network.NetworkRepository

class GetMoviesUseCase(private val repository: NetworkRepository) {
    suspend fun start() = repository.getMovies()
}