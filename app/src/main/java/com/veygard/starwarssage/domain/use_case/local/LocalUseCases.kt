package com.veygard.starwarssage.domain.use_case.local

data class LocalUseCases(
    val getLocalMoviesListUseCase: GetLocalMoviesListUseCase,
    val getLocalPersonUseCase: GetLocalPersonUseCase,
    val getLocalPlanetUseCase: GetLocalPlanetUseCase,
    val getLocalMovieUseCase: GetLocalMovieUseCase
)