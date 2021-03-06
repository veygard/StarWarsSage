package com.veygard.starwarssage.domain.repository.network

import android.util.Log
import com.veygard.starwarssage.data.network.api.StarWarsApi
import com.veygard.starwarssage.domain.model.PersonWithAvatar
import com.veygard.starwarssage.domain.repository.local.LocalDbRepository
import com.veygard.starwarssage.domain.response.ApiResponseType
import com.veygard.starwarssage.domain.response.RequestResult
import com.veygard.starwarssage.util.Constants.AVATAR_URL
import com.veygard.starwarssage.util.Constants.AVATAR_URL_ENDINGS
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val starWarsApi: StarWarsApi,
    private val localDbRepository: LocalDbRepository
) :
    NetworkRepository {

    override suspend fun getAllMovies(): RequestResult {
        var result: RequestResult =
            RequestResult.EnqueueError("StarWarsRepositoryImpl getMovies not working")

        try {
            val call = starWarsApi.getAllFilmsApi()
            when {
                call.isSuccessful -> {
                    call.body()?.let {

                        it.results?.let { localDbRepository.insertMovies(it) }
                        result = RequestResult.Success(ApiResponseType.GetAllMovies(it))

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

    override suspend fun getMovie(index: Int): RequestResult {
        var result: RequestResult =
            RequestResult.EnqueueError("StarWarsRepositoryImpl getMovies not working")
        try {
            val call = starWarsApi.getMovieApi(index)
            when {
                call.isSuccessful -> {
                    call.body()?.let {
                        result = RequestResult.Success(ApiResponseType.GetMovie(it))
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
                        val person = it
                        getPersonAvatar(index.toString())?.let {  pwa->
                            if(pwa.name == person.name) person.avatarUrl = pwa.image
                        }
                        result = RequestResult.Success(ApiResponseType.GetPerson(person))
                        localDbRepository.insertPerson(person)

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

    override suspend fun getPersonAvatar(index: String?): PersonWithAvatar? {
        var person: PersonWithAvatar? = null
        index?.let { i ->
            try {
                val url = AVATAR_URL + i + AVATAR_URL_ENDINGS
                val call = starWarsApi.getAvatarUrl(url)
                when {
                    call.isSuccessful -> {
                        call.body()?.let {
                            person = it
                        }
                    }
                    else -> {}
                }
            } catch (e: Exception) {
            }
        }
        return person
    }

    override suspend fun getPlanet(index: Int): RequestResult {
        var result: RequestResult =
            RequestResult.EnqueueError("StarWarsRepositoryImpl getMovies not working")
        try {
            val call = starWarsApi.getPlanetApi(index)
            when {
                call.isSuccessful -> {
                    call.body()?.let {
                        result = RequestResult.Success(ApiResponseType.GetPlanet(it))
                        localDbRepository.insertPlanet(it)
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

    /*todo ?????? ???????????? ???????? ??????-???? ???? rxJava, ???? ???? ????????*/
    override suspend fun downloadPlanets() {
        var pageCounter = 0
        var gotMorePages = true
        try {
            while (gotMorePages) {
                val call = getPlanetByPage(if (pageCounter == 0) null else pageCounter)
                when {
                    call.isSuccessful -> {
                        call.body()?.next?.let {
                            gotMorePages = true
                            pageCounter++

                            call.body()?.results?.let {
                                it.forEach { planet ->
                                    localDbRepository.insertPlanet(planet)
                                }
                            }
                        } ?: run {
                            gotMorePages = false
                            pageCounter = 0
                            call.body()?.results?.let {
                                it.forEach { planet ->
                                    localDbRepository.insertPlanet(planet)
                                }
                            }
                        }
                    }
                    else -> {
                        gotMorePages = true
                    }
                }
            }
        } catch (e: Exception) {
            gotMorePages = true
        }
    }

    /*todo ?????? ???????????? ???????? ??????-???? ???? rxJava, ???? ???? ????????*/
    override suspend fun downloadPeople() {
        var pageCounter = 0
        var gotMorePages = true

        try {
            while (gotMorePages) {
                val call = getPeopleByPage(if (pageCounter == 0) null else pageCounter)
                when {
                    call.isSuccessful -> {
                        call.body()?.next?.let {
                            gotMorePages = true
                            pageCounter++
                            call.body()?.results?.let {
                                it.forEach { person ->
                                    val p= person
                                    val indexStr =
                                        person.url?.substringAfter("people/")?.replace("/", "")
                                    getPersonAvatar(indexStr)?.let {  pwa->
                                        if(pwa.name == p.name) p.avatarUrl = pwa.image
                                    }
                                    localDbRepository.insertPerson(p)
                                }
                            }
                        } ?: run {
                            gotMorePages = false
                            pageCounter = 0
                            call.body()?.results?.let {
                                it.forEach { person ->
                                    localDbRepository.insertPerson(person)
                                }
                            }
                        }
                    }

                    else -> {
                        gotMorePages = true
                    }
                }
            }
        } catch (e: Exception) {
            gotMorePages = true
        }
    }

    private suspend fun getPlanetByPage(page: Int?) = starWarsApi.getAllPlanets(page)
    private suspend fun getPeopleByPage(page: Int?) = starWarsApi.getAllPeople(page)
}