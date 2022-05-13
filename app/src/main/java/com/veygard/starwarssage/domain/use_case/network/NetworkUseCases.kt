package com.veygard.starwarssage.domain.use_case.network


data class NetworkUseCases(
    val getMoviesUseCase: GetMoviesUseCase,
    val getPersonUseCase: GetPersonUseCase,
    val getPlanetUseCase: GetPlanetUseCase,
    val getPlanetsUseCase: GetPlanetsUseCase,
    val getPeopleUseCase: GetPeopleUseCase,
)