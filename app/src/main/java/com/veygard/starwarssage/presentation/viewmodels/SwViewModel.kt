package com.veygard.starwarssage.presentation.viewmodels

import android.util.Log
import com.veygard.starwarssage.domain.model.Movie
import com.veygard.starwarssage.domain.model.Person
import com.veygard.starwarssage.domain.response.ApiResponseType
import com.veygard.starwarssage.domain.response.RequestResult
import com.veygard.starwarssage.domain.use_case.local.LocalUseCases
import com.veygard.starwarssage.domain.use_case.network.NetworkUseCases
import com.veygard.starwarssage.presentation.adapters.ClickInterface
import com.veygard.starwarssage.presentation.adapters.MovieClickInterface
import com.veygard.starwarssage.util.ToastTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SwViewModel @Inject constructor(
    private val networkUseCases: NetworkUseCases,
    private val localUseCases: LocalUseCases,
) : ViewModel() {


    private val _viewModelState: MutableLiveData<SwViewModelState?> = MutableLiveData(null)
    val viewModelState: LiveData<SwViewModelState?> = _viewModelState

    private var originalMovieList: List<Movie>? = null
    private val _moviesListToShow: MutableLiveData<List<Movie>?> = MutableLiveData(null)
    val moviesListToShow: LiveData<List<Movie>?> = _moviesListToShow

    private var originalPersonList: List<Person>? = null
    private val _peopleToShow: MutableLiveData<List<Person>?> =
        MutableLiveData(null)
    val peopleToShow: LiveData<List<Person>?> = _peopleToShow

    private val _loadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingState: LiveData<Boolean> = _loadingState

    private val _showToast: MutableLiveData<ToastTypes?> = MutableLiveData(null)
    val showToast: LiveData<ToastTypes?> = _showToast


    fun getMovies() {
        viewModelScope.launch {
            _viewModelState.value = SwViewModelState.Loading
            val result = getLocalMovies()
            when {
                result.isEmpty() -> getMoviesFromServer()
                result.isNotEmpty() -> {
                    originalMovieList = result.sortedBy { it.episode_id }
                    _moviesListToShow.value = originalMovieList
                    _viewModelState.value = SwViewModelState.GotMoviesLocal
                }
            }
        }
    }

    fun getMovie(url: String) {
        viewModelScope.launch {
            _viewModelState.value = SwViewModelState.Loading
            val result = localUseCases.getLocalMovieUseCase.start(url)
            result?.let {
                _viewModelState.value = SwViewModelState.GotMovie(it)
            } ?: kotlin.run {
                getMovieFromServer(url)
            }
        }
    }

    private fun getMovieFromServer(url: String) {
        viewModelScope.launch {
            try {
                val indexStr = url.substringAfter("films/").replace("/", "")
                val index = indexStr.toInt()

                when (val result = networkUseCases.getMovieUseCase.start(index)) {
                    is RequestResult.Success -> {
                        (result.response as ApiResponseType.GetMovie).movie.let {
                            _viewModelState.value = SwViewModelState.GotMovie(it)
                        }
                    }

                    is RequestResult.ConnectionError -> {
                        _showToast.value = ToastTypes.ConnectionError
                        _viewModelState.value = SwViewModelState.ServerError(result)
                    }

                    is RequestResult.ServerError -> {
                        _showToast.value = ToastTypes.ServerError
                        _viewModelState.value = SwViewModelState.ServerError(result)
                    }
                }
            } catch (e: Exception) {
                _showToast.value = ToastTypes.AppError
            }
        }
    }

    private var getPeopleByMovieJob = Job()
        get() {
            if (field.isCancelled) field = Job()
            return field
        }

    fun getPeopleByMovie(movie: Movie) {
        viewModelScope.launch(getPeopleByMovieJob) {
            _peopleToShow.value = null
            _viewModelState.value = SwViewModelState.Loading
            Log.e("get_ppl_result", "movie url: ${movie.url}")
            //получаем персонажей из бд или от сервера
            val personList = mutableSetOf<Person>()
            movie.characters?.forEach { personUrl ->
                val result = localUseCases.getLocalPersonUseCase.start(personUrl)
                Log.e("get_ppl_result", "person: ${result?.name ?: "not found"}")
                result?.let {
                    personList.add(it)
                    Log.e("get_ppl_result", "person local found: ${result.name}")
                } ?: kotlin.run {
                    try {
                        val indexStr = personUrl.substringAfter("people/").replace("/", "")
                        val index = indexStr.toInt()
                        when (val serverResult = networkUseCases.getPersonUseCase.start(index)) {
                            is RequestResult.Success -> {
                                (serverResult.response as ApiResponseType.GetPerson).person.let {
                                    personList.add(it)
                                }
                                Log.e("get_ppl_result", "person server download: ${result?.name}")
                            }
                            else -> {}
                        }
                    } catch (e: Exception) {
                        _showToast.value = ToastTypes.AppError
                    }
                }
            }
            //показываем получившийся список
            _peopleToShow.value = personList.toList()
            originalPersonList = _peopleToShow.value?.toList()
            _viewModelState.value = SwViewModelState.GotPeopleByMovie
        }
    }

    fun cancelGetPeopleByMovieJob() {
        if (getPeopleByMovieJob.isActive) getPeopleByMovieJob.cancel()
    }

    fun setStateNull() {
        _viewModelState.value = null
    }

    fun filterMoviesBySearch(value: String) {
        viewModelScope.launch {

            when {
                value.isEmpty() -> {
                    _viewModelState.value = SwViewModelState.GotMoviesLocal
                    _moviesListToShow.value = originalMovieList
                }
                else -> {
                    val newList = originalMovieList?.filter {
                        it.title?.contains(
                            value,
                            ignoreCase = true
                        ) == true
                    }
                        ?: emptyList()
                    when {
                        newList.isEmpty() -> _viewModelState.value = SwViewModelState.NotFound
                        newList.isNotEmpty() -> {
                            _viewModelState.value = SwViewModelState.GotMoviesLocal
                            _moviesListToShow.value = newList
                        }
                    }
                }
            }
        }
    }

    fun filterPeopleBySearch(value: String) {
        viewModelScope.launch {
            when {
                value.isEmpty() -> {
                    _viewModelState.value = SwViewModelState.GotPeopleByMovie
                    _peopleToShow.value = originalPersonList
                }
                else -> {
                    val newSet = originalPersonList?.filter {
                        it.name?.contains(
                            value,
                            ignoreCase = true
                        ) == true
                    }
                        ?: emptyList()
                    when {
                        newSet.isEmpty() -> _viewModelState.value = SwViewModelState.NotFound
                        newSet.isNotEmpty() -> {
                            _viewModelState.value = SwViewModelState.GotPeopleByMovie
                            _peopleToShow.value = newSet
                        }
                    }
                }
            }
        }
    }

    private suspend fun getLocalMovies(): List<Movie> {
        return localUseCases.getLocalMoviesListUseCase.start()
    }

    private fun getMoviesFromServer() {
        viewModelScope.launch {
            delay(2000) //показ шиммера
            when (val result = networkUseCases.getAllMoviesUseCase.start()) {
                is RequestResult.Success -> {
                    (result.response as ApiResponseType.GetAllMovies).getMoviesResponse.results?.let {
                        _viewModelState.value = SwViewModelState.GotMovies
                        originalMovieList = it.sortedBy { it.episode_id }
                        _moviesListToShow.value = originalMovieList

                        Log.e("get_movies", "state got list ")
                    } ?: run {
                        Log.e("get_movies", "state got error")
                        _viewModelState.value =
                            SwViewModelState.ServerError(RequestResult.NoMoviesError("don't have movies"))
                    }
                    networkUseCases.getPeopleUseCase.start()
                    networkUseCases.getPlanetsUseCase.start()
                    Log.e("get_movies", "state ended ")
                }

                is RequestResult.ConnectionError -> {
                    _showToast.value = ToastTypes.ConnectionError
                    _viewModelState.value = SwViewModelState.ServerError(result)
                }

                is RequestResult.ServerError -> {
                    _showToast.value = ToastTypes.ServerError
                    _viewModelState.value = SwViewModelState.ServerError(result)
                }
            }
        }
    }

    fun getPerson(index: Int) {
        viewModelScope.launch {
            Log.e("button_click", "person VM started")
            val result = networkUseCases.getPersonUseCase.start(index)

            when (result) {
                is RequestResult.Success -> {
                    val getPerson = result.response as ApiResponseType.GetPerson
                    _viewModelState.value = SwViewModelState.GotPerson(getPerson.person)
                }

                is RequestResult.ConnectionError -> {
                    _showToast.value = ToastTypes.ConnectionError
                }

                is RequestResult.ServerError -> {
                    _showToast.value = ToastTypes.ServerError
                }
            }
        }
    }

    fun getPlanet(index: Int) {
        viewModelScope.launch {
            when (val result = networkUseCases.getPlanetUseCase.start(index)) {
                is RequestResult.Success -> {
                    val getPlanet = result.response as ApiResponseType.GetPlanet
                    _viewModelState.value = SwViewModelState.GotPlanet(getPlanet.planet)
                }

                is RequestResult.ConnectionError -> {
                    _showToast.value = ToastTypes.ConnectionError
                }

                is RequestResult.ServerError -> {
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