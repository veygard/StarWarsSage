package com.veygard.starwarssage.domain.use_case

import com.veygard.starwarssage.domain.repository.StarWarsRepository

class GetPersonUseCase(private val repository: StarWarsRepository) {
    suspend fun start(index:Int) = repository.getPerson(index)
}