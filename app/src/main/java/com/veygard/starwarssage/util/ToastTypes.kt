package com.veygard.starwarssage.util

sealed class ToastTypes{
    object ConnectionError: ToastTypes()
    object ServerError: ToastTypes()
    object AppError: ToastTypes()
    data class DownloadPeople(val current:String, val all: String) : ToastTypes()
    object DownloadMovie : ToastTypes()
    data class RequestTimeOut(val pauseTime: Long): ToastTypes()
    object Loading: ToastTypes()
    object NoMoviesLeft: ToastTypes()
}
