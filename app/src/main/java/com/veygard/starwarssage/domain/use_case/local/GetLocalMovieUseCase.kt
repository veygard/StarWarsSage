package com.veygard.starwarssage.domain.use_case.local

import com.veygard.starwarssage.domain.repository.local.LocalDbRepository


class GetLocalMovieUseCase(private val localDbRepository: LocalDbRepository) {
    suspend fun start(url:String) = localDbRepository.getMovie(url)
}