package com.veygard.starwarssage.domain.use_case

import com.veygard.starwarssage.domain.repository.network.NetworkRepository

class GetPlanetsUseCase(private val repository: NetworkRepository) {
    suspend fun start() = repository.getPlanets()
}