package com.veygard.starwarssage.domain.use_case

import com.veygard.starwarssage.domain.repository.network.StarWarsRepository

class GetPeopleUseCase(private val repository: StarWarsRepository) {
    suspend fun start() = repository.getPeople()
}