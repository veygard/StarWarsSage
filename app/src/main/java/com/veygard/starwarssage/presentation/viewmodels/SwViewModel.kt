package com.veygard.starwarssage.presentation.viewmodels

import android.util.Log
import com.veygard.starwarssage.domain.model.Movie
import com.veygard.starwarssage.domain.response.ApiResponseType
import com.veygard.starwarssage.domain.response.RequestResult
import com.veygard.starwarssage.domain.use_case.local.LocalUseCases
import com.veygard.starwarssage.domain.use_case.network.NetworkUseCases
import com.veygard.starwarssage.util.ToastTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SwViewModel @Inject constructor(
    private val networkUseCases: NetworkUseCases,
    private val localUseCases: LocalUseCases,
) : ViewModel() {

    private var originalMovieList: List<Movie>? = null

    private val _viewModelState: MutableLiveData<SwViewModelState?> = MutableLiveData(null)
    val viewModelState: LiveData<SwViewModelState?> = _viewModelState

    private val _moviesListToShow: MutableLiveData<List<Movie>?> = MutableLiveData(null)
    val moviesListToShow: LiveData<List<Movie>?> = _moviesListToShow


    private val _loadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingState: LiveData<Boolean> = _loadingState

    private val _showToast: MutableLiveData<ToastTypes?> = MutableLiveData(null)
    val showToast: LiveData<ToastTypes?> = _showToast

    var filterValue = ""


    fun getMovies() {
        viewModelScope.launch {
            val result = getLocalMovies()
            when {
                result.isEmpty() -> getMoviesFromServer()
                result.isNotEmpty() -> {
                    originalMovieList = result.sortedBy { it.episode_id }
                    _moviesListToShow.value =originalMovieList
                    _viewModelState.value = SwViewModelState.GotMoviesLocal
                }
            }
        }
    }

    fun filterMoviesBySearch(value: String) {
        viewModelScope.launch {
            filterValue = value

            when {
                value.isEmpty() -> {
                    _viewModelState.value = SwViewModelState.GotMoviesLocal
                    _moviesListToShow.value = originalMovieList
                }
                else -> {
                    _viewModelState.value = SwViewModelState.GotMoviesLocal
                    _moviesListToShow.value = originalMovieList?.filter { it.title?.contains(value,ignoreCase = true) == true } ?: emptyList()
                }
            }

        }
    }

    private suspend fun getLocalMovies(): List<Movie> {
        return localUseCases.getLocalMoviesUseCase.start()
    }

    private fun getMoviesFromServer() {
        viewModelScope.launch {
            _loadingState.value = true
            when (val result = networkUseCases.getMoviesUseCase.start()) {
                is RequestResult.Success -> {
                    _loadingState.value = false
                    (result.response as ApiResponseType.GetMovies).getMoviesResponse.results?.let {
                        _viewModelState.value = SwViewModelState.GotMovies
                        originalMovieList = it.sortedBy { it.episode_id }
                        _moviesListToShow.value = originalMovieList

                        Log.e("get_movies", "state got list ")
                    } ?: run {
                        Log.e("get_movies", "state got error")
                        _viewModelState.value =
                            SwViewModelState.Error(RequestResult.NoMoviesError("don't have movies"))
                    }

                    Log.e("get_movies", "state ended ")

                    networkUseCases.getPlanetsUseCase.start()
                    networkUseCases.getPeopleUseCase.start()
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
            val result = networkUseCases.getPersonUseCase.start(index)

            when (result) {
                is RequestResult.Success -> {
                    _loadingState.value = false
                    val getPerson = result.response as ApiResponseType.GetPerson
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
            when (val result = networkUseCases.getPlanetUseCase.start(index)) {
                is RequestResult.Success -> {
                    _loadingState.value = false
                    val getPlanet = result.response as ApiResponseType.GetPlanet
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

    fun getPlanets() {
        viewModelScope.launch {
            Log.e("bd_download", "get planets VM start")
            networkUseCases.getPlanetsUseCase.start()
        }
    }

    fun getLocalPerson(url: String) {
        viewModelScope.launch {
            localUseCases.getLocalPersonUseCase.start(url)
        }
    }

    fun getLocalPlanet(url: String) {
        viewModelScope.launch {
            localUseCases.getLocalPlanetUseCase.start(url)
        }
    }

    fun getPeople() {
        viewModelScope.launch {
            Log.e("bd_download", "get people VM start")
            networkUseCases.getPeopleUseCase.start()
        }
    }
}