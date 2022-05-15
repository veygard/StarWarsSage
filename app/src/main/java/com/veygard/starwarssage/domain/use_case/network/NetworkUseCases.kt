package com.veygard.starwarssage.domain.use_case.network


data class NetworkUseCases(
    val getAllMoviesUseCase: GetAllMoviesUseCase,
    val getPersonUseCase: GetPersonUseCase,
    val getPlanetUseCase: GetPlanetUseCase,
    val getPlanetsUseCase: GetPlanetsUseCase,
    val getPeopleUseCase: GetPeopleUseCase,
    val getMovieUseCase: GetMovieUseCase
)