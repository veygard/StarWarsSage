package com.veygard.starwarssage.presentation.viewmodels

import com.veygard.starwarssage.data.network.response.GetMoviesResponse
import com.veygard.starwarssage.data.network.response.Person
import com.veygard.starwarssage.data.network.response.Planet
import com.veygard.starwarssage.domain.response.ApiResponseType
import com.veygard.starwarssage.domain.response.RequestResult

sealed class SwViewModelState {
    data class GotMovies(val getMovies: ApiResponseType.GetMovies ): SwViewModelState()
    data class GotPerson(val person: Person): SwViewModelState()
    data class GotPlanet(val planet: Planet): SwViewModelState()
    object MoreMoviesError: SwViewModelState()
    data class Error(val result: RequestResult, val msg:String?): SwViewModelState()
    object Loading: SwViewModelState()
}