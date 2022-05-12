package com.veygard.starwarssage.domain.repository

import android.util.Log
import com.veygard.starwarssage.data.network.api.StarWarsApi
import com.veygard.starwarssage.domain.response.ApiResponseType
import com.veygard.starwarssage.domain.response.RequestResult
import javax.inject.Inject

class StarWarsRepositoryImpl@Inject constructor(private val starWarsApi: StarWarsApi) : StarWarsRepository {

    override suspend fun getMovies(): RequestResult {
        var result: RequestResult = RequestResult.EnqueueError("StarWarsRepositoryImpl getMovies not working")
        try {
            val call =  starWarsApi.getAllFilmsApi()
            when{
                call.isSuccessful->{
                    call.body()?.let {
                        result = RequestResult.Success(ApiResponseType.GetMovies(it))
                    } ?: run {
                        result = RequestResult.ServerError(
                            error = call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        )
                    }
                }
                call.code() in 400..499 ->{
                    result = RequestResult.ServerError(
                        error = "Client Error: ${call.errorBody()?.source()?.buffer?.snapshot()?.utf8()}"
                    )
                }
                call.code() in 500..599 ->{
                    result = RequestResult.ServerError(
                        error = "Server Error: ${call.errorBody()?.source()?.buffer?.snapshot()?.utf8()}"
                    )

                }
                else -> {
                    result = RequestResult.ServerError(
                        error = "Unknown Error: ${call.errorBody()?.source()?.buffer?.snapshot()?.utf8()}"
                    )
                }
            }
        } catch (e: Exception) {
            result = RequestResult.ConnectionError()
        }
        return result
    }

    override suspend fun getPerson(index: Int): RequestResult {
        var result: RequestResult = RequestResult.EnqueueError("StarWarsRepositoryImpl getMovies not working")
        Log.e("button_click", "getPerson repository start")
        try {
            val call =  starWarsApi.getPersonApi(index)
            when{
                call.isSuccessful->{
                    call.body()?.let {
                        result = RequestResult.Success(ApiResponseType.GetPerson(it))
                    } ?: run {
                        result = RequestResult.ServerError(
                            error = call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        )
                    }
                }
                call.code() in 400..499 ->{
                    result = RequestResult.ServerError(
                        error = "Client Error: ${call.errorBody()?.source()?.buffer?.snapshot()?.utf8()}"
                    )
                }
                call.code() in 500..599 ->{
                    result = RequestResult.ServerError(
                        error = "Server Error: ${call.errorBody()?.source()?.buffer?.snapshot()?.utf8()}"
                    )

                }
                else -> {
                    result = RequestResult.ServerError(
                        error = "Unknown Error: ${call.errorBody()?.source()?.buffer?.snapshot()?.utf8()}"
                    )
                }
            }
        } catch (e: Exception) {
            result = RequestResult.ConnectionError()
        }
        return result
    }

    override suspend fun getPlanet(index: Int): RequestResult {
        var result: RequestResult = RequestResult.EnqueueError("StarWarsRepositoryImpl getMovies not working")
        try {
            val call =  starWarsApi.getPlanetApi(index)
            when{
                call.isSuccessful->{
                    call.body()?.let {
                        result = RequestResult.Success(ApiResponseType.GetPlanet(it))
                    } ?: run {
                        result = RequestResult.ServerError(
                            error = call.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        )
                    }
                }
                call.code() in 400..499 ->{
                    result = RequestResult.ServerError(
                        error = "Client Error: ${call.errorBody()?.source()?.buffer?.snapshot()?.utf8()}"
                    )
                }
                call.code() in 500..599 ->{
                    result = RequestResult.ServerError(
                        error = "Server Error: ${call.errorBody()?.source()?.buffer?.snapshot()?.utf8()}"
                    )

                }
                else -> {
                    result = RequestResult.ServerError(
                        error = "Unknown Error: ${call.errorBody()?.source()?.buffer?.snapshot()?.utf8()}"
                    )
                }
            }
        } catch (e: Exception) {
            result = RequestResult.ConnectionError()
        }
        return result
    }
}