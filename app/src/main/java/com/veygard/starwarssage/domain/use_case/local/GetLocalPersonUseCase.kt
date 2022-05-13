package com.veygard.starwarssage.domain.use_case.local

import com.veygard.starwarssage.domain.repository.local.LocalDbRepository


class GetLocalPersonUseCase(private val localDbRepository: LocalDbRepository) {
    suspend fun start(url:String) = localDbRepository.getPerson(url)
}