package com.veygard.starwarssage.domain.use_case

import com.veygard.starwarssage.domain.repository.network.NetworkRepository

class GetPersonUseCase(private val repository: NetworkRepository) {
    suspend fun start(index:Int) = repository.getPerson(index)
}