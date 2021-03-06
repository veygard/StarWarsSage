package com.veygard.starwarssage.presentation.viewmodels

import com.veygard.starwarssage.domain.model.Movie
import com.veygard.starwarssage.domain.model.Person
import com.veygard.starwarssage.domain.model.Planet
import com.veygard.starwarssage.domain.response.ApiResponseType
import com.veygard.starwarssage.domain.response.RequestResult

sealed class SwViewModelState {
    object GotMovies: SwViewModelState()
    data class GotMovie(val movie: Movie): SwViewModelState()
    object GotPeopleByMovie: SwViewModelState()
    object NotFound: SwViewModelState()
    object Loading: SwViewModelState()
    data class GotPerson(val person: Person): SwViewModelState()
    data class GotPlanet(val planet: Planet): SwViewModelState()
    data class ServerError(val result: RequestResult): SwViewModelState()
}