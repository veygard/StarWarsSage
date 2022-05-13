package com.veygard.starwarssage.domain.repository.network

import android.util.Log
import com.veygard.starwarssage.data.network.api.StarWarsApi
import com.veygard.starwarssage.domain.model.Person
import com.veygard.starwarssage.domain.model.Planet
import com.veygard.starwarssage.domain.repository.local.LocalDbRepository
import com.veygard.starwarssage.domain.response.ApiResponseType
import com.veygard.starwarssage.domain.response.RequestResult
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(private val starWarsApi: StarWarsApi, private val localDbRepository: LocalDbRepository) :
    NetworkRepository {

    override suspend fun getMovies(): RequestResult {
        var result: RequestResult =
            RequestResult.EnqueueError("StarWarsRepositoryImpl getMovies not working")
        try {
            val call = starWarsApi.getAllFilmsApi()
            when {
                call.isSuccessful -> {
                    call.body()?.let {

                        it.results?.let{ localDbRepository.insertMovies(it)}

                        result = RequestResult.Success(ApiResponseType.GetMovies(it))
                    } ?: run {
                        result = RequestResult.ServerError(
                            error = call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        )
                    }
                }
                call.code() in 400..499 -> {
                    result = RequestResult.ServerError(
                        error = "Client Error: ${
                            call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        }"
                    )
                }
                call.code() in 500..599 -> {
                    result = RequestResult.ServerError(
                        error = "Server Error: ${
                            call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        }"
                    )

                }
                else -> {
                    result = RequestResult.ServerError(
                        error = "Unknown Error: ${
                            call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        }"
                    )
                }
            }
        } catch (e: Exception) {
            result = RequestResult.ConnectionError()
        }
        return result
    }


    override suspend fun getPerson(index: Int): RequestResult {
        var result: RequestResult =
            RequestResult.EnqueueError("StarWarsRepositoryImpl getMovies not working")
        Log.e("button_click", "getPerson repository start")
        try {
            val call = starWarsApi.getPersonApi(index)
            when {
                call.isSuccessful -> {
                    call.body()?.let {
                        result = RequestResult.Success(ApiResponseType.GetPerson(it))
                    } ?: run {
                        result = RequestResult.ServerError(
                            error = call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        )
                    }
                }
                call.code() in 400..499 -> {
                    result = RequestResult.ServerError(
                        error = "Client Error: ${
                            call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        }"
                    )
                }
                call.code() in 500..599 -> {
                    result = RequestResult.ServerError(
                        error = "Server Error: ${
                            call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        }"
                    )

                }
                else -> {
                    result = RequestResult.ServerError(
                        error = "Unknown Error: ${
                            call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        }"
                    )
                }
            }
        } catch (e: Exception) {
            result = RequestResult.ConnectionError()
        }
        return result
    }

    /*todo тут должно быть что-то на rxJava, но не умею*/
    override suspend fun getPlanet(index: Int): RequestResult {
        var result: RequestResult =
            RequestResult.EnqueueError("StarWarsRepositoryImpl getMovies not working")
        try {
            val call = starWarsApi.getPlanetApi(index)
            when {
                call.isSuccessful -> {
                    call.body()?.let {
                        result = RequestResult.Success(ApiResponseType.GetPlanet(it))

                    } ?: run {
                        result = RequestResult.ServerError(
                            error = call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        )
                    }
                }
                call.code() in 400..499 -> {
                    result = RequestResult.ServerError(
                        error = "Client Error: ${
                            call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        }"
                    )
                }
                call.code() in 500..599 -> {
                    result = RequestResult.ServerError(
                        error = "Server Error: ${
                            call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        }"
                    )

                }
                else -> {
                    result = RequestResult.ServerError(
                        error = "Unknown Error: ${
                            call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        }"
                    )
                }
            }
        } catch (e: Exception) {
            result = RequestResult.ConnectionError()
        }
        return result
    }

    /*todo тут должно быть что-то на rxJava, но не умею*/
    override suspend fun getPlanets() {
        val planetList = mutableListOf<Planet>()
        var pageCounter = 0
        var gotMorePages = true

        while (gotMorePages) {
            try {
                val call = getPlanetByPage(if (pageCounter == 0) null else pageCounter)
                when {
                    call.isSuccessful -> {
                        call.body()?.next?.let {
                            gotMorePages = true
                            pageCounter++

                            call.body()?.results?.let {
                                planetList.addAll(it)
                                it.forEach { planet->
                                    localDbRepository.insertPlanet(planet)
                                }

                            }
                            Log.e("bd_download", "planet list size: ${planetList.size}")

                        } ?: run {
                            gotMorePages = false
                            pageCounter = 0
                            Log.e("bd_download", "planet don't have more pages")
                            call.body()?.results?.let {
                                planetList.addAll(it)
                            }
                            Log.e("bd_download", "planet list size: ${planetList.size}")
                        }
                    }

                    else -> {
                        gotMorePages = true
                    }
                }
            } catch (e: Exception) {
                gotMorePages = true
            }
        }
    }

    override suspend fun getPeople() {
        val personList = mutableListOf<Person>()
        var pageCounter = 0
        var gotMorePages = true

        while (gotMorePages) {
            try {
                val call = getPeopleByPage(if (pageCounter == 0) null else pageCounter)
                when {
                    call.isSuccessful -> {
                        call.body()?.next?.let {
                            gotMorePages = true
                            pageCounter++

                            call.body()?.results?.let {
                                personList.addAll(it)
                                it.forEach { person->
                                    localDbRepository.insertPerson(person)
                                }
                            }
                            Log.e("bd_download", "personList size: ${personList.size}")

                        } ?: run {
                            gotMorePages = false
                            pageCounter = 0
                            call.body()?.results?.let {
                                personList.addAll(it)
                            }
                            Log.e("bd_download", "personList size: ${personList.size}")
                            Log.e("bd_download", "people don't have more pages")
                        }
                    }

                    else -> {
                        gotMorePages = true
                    }
                }
            } catch (e: Exception) {
                gotMorePages = true
            }
        }
    }

    private suspend fun getPlanetByPage(page: Int?) = starWarsApi.getAllPlanets(page)
    private suspend fun getPeopleByPage(page: Int?) = starWarsApi.getAllPeople(page)
}