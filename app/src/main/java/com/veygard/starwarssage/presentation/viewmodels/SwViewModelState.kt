package com.veygard.starwarssage.presentation.viewmodels

import com.veygard.starwarssage.domain.model.Movie
import com.veygard.starwarssage.domain.model.Person
import com.veygard.starwarssage.domain.model.Planet
import com.veygard.starwarssage.domain.response.ApiResponseType
import com.veygard.starwarssage.domain.response.RequestResult

sealed class SwViewModelState {
    data class GotMovies(val getMovies: ApiResponseType.GetMovies ): SwViewModelState()
    data class GotMoviesLocal(val list: List<Movie> ): SwViewModelState()
    data class GotPerson(val person: Person): SwViewModelState()
    data class GotPlanet(val planet: Planet): SwViewModelState()
    data class Error(val result: RequestResult, val msg:String?): SwViewModelState()
}