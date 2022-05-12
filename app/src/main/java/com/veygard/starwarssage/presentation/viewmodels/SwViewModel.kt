package com.veygard.starwarssage.presentation.viewmodels

import android.util.Log
import com.veygard.starwarssage.domain.response.ApiResponseType
import com.veygard.starwarssage.domain.response.RequestResult
import com.veygard.starwarssage.domain.use_case.StarWarsUseCases
import com.veygard.starwarssage.util.ToastTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SwViewModel @Inject constructor(
    private val starWarsUseCases: StarWarsUseCases
) : ViewModel() {

    private val _viewModelState: MutableLiveData<SwViewModelState?> = MutableLiveData(null)
    val viewModelState: LiveData<SwViewModelState?> = _viewModelState

    private val _loadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingState: LiveData<Boolean> = _loadingState

    private val _showToast: MutableLiveData<ToastTypes?> = MutableLiveData(null)
    val showToast: LiveData<ToastTypes?> = _showToast

    fun getMovies() {
        viewModelScope.launch {
            _loadingState.value  = true
            when (val result = starWarsUseCases.getMoviesUseCase.start()) {
                is RequestResult.Success -> {
                    _loadingState.value = false
                    _viewModelState.value = SwViewModelState.GotMovies(result.response as ApiResponseType.GetMovies)
                }

                is RequestResult.ConnectionError -> {
                    _loadingState.value = false
                    _showToast.value = ToastTypes.ConnectionError
                }

                is RequestResult.ServerError -> {
                    _loadingState.value = false
                    _showToast.value = ToastTypes.ServerError
                }
            }
        }
    }

    fun getPerson(index: Int) {
        viewModelScope.launch {
            _loadingState.value = true
            Log.e("button_click", "person VM started")
            val result = starWarsUseCases.getPersonUseCase.start(index)

            when (result) {
                is RequestResult.Success -> {
                    _loadingState.value = false
                    val getPerson= result.response as ApiResponseType.GetPerson
                    _viewModelState.value = SwViewModelState.GotPerson(getPerson.person)
                }

                is RequestResult.ConnectionError -> {
                    _loadingState.value = false
                    _showToast.value = ToastTypes.ConnectionError
                }

                is RequestResult.ServerError -> {
                    _loadingState.value = false
                    _showToast.value = ToastTypes.ServerError
                }
            }
        }
    }

    fun getPlanet(index: Int) {
        viewModelScope.launch {
            _loadingState.value = true
            when (val result = starWarsUseCases.getPlanetUseCase.start(index)) {
                is RequestResult.Success -> {
                    _loadingState.value = false
                    val getPlanet= result.response as ApiResponseType.GetPlanet
                    _viewModelState.value = SwViewModelState.GotPlanet(getPlanet.planet)
                }

                is RequestResult.ConnectionError -> {
                    _loadingState.value = false
                    _showToast.value = ToastTypes.ConnectionError
                }

                is RequestResult.ServerError -> {
                    _loadingState.value = false
                    _showToast.value = ToastTypes.ServerError
                }
            }
        }
    }


}