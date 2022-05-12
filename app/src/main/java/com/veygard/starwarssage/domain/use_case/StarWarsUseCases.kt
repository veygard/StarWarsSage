package com.veygard.starwarssage.domain.use_case

data class StarWarsUseCases(
    val getMoviesUseCase: GetMoviesUseCase,
    val getPersonUseCase: GetPersonUseCase,
    val getPlanetUseCase: GetPlanetUseCase,
    val getPlanetsUseCase: GetPlanetsUseCase,
    val getPeopleUseCase: GetPeopleUseCase,
    val getLocalMoviesUseCase: GetLocalMoviesUseCase
)