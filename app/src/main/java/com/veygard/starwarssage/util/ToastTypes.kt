package com.veygard.starwarssage.util

sealed class ToastTypes{
    object ConnectionError: ToastTypes()
    object ServerError: ToastTypes()
    data class RequestTimeOut(val pauseTime: Long): ToastTypes()
    object Loading: ToastTypes()
    object NoMoviesLeft: ToastTypes()
}
