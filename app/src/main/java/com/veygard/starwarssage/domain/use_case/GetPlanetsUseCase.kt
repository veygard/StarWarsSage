package com.veygard.starwarssage.domain.use_case

import com.veygard.starwarssage.domain.repository.StarWarsRepository

class GetPlanetsUseCase(private val repository: StarWarsRepository) {
    suspend fun start() = repository.getPlanets()
}