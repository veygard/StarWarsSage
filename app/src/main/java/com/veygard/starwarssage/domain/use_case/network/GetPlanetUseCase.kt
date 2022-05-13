package com.veygard.starwarssage.domain.use_case.network

import com.veygard.starwarssage.domain.repository.network.NetworkRepository

class GetPlanetUseCase(private val repository: NetworkRepository) {
    suspend fun start(index:Int) = repository.getPlanet(index)
}