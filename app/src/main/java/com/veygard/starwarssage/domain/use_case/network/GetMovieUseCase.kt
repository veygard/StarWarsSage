package com.veygard.starwarssage.domain.use_case.network

import com.veygard.starwarssage.domain.repository.network.NetworkRepository

class GetMovieUseCase(private val repository: NetworkRepository) {
    suspend fun start(index:Int) = repository.getMovie(index)
}