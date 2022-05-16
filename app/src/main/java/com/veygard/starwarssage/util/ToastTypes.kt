package com.veygard.starwarssage.util

sealed class ToastTypes{
    object ConnectionError: ToastTypes()
    object ServerError: ToastTypes()
    object AppError: ToastTypes()
    data class Download(val current:String, val all: String) : ToastTypes()
    data class RequestTimeOut(val pauseTime: Long): ToastTypes()
    object Loading: ToastTypes()
    object NoMoviesLeft: ToastTypes()
}
