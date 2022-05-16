package com.veygard.starwarssage.presentation.widgets

import android.content.Context
import com.veygard.starwarssage.R
import com.veygard.starwarssage.util.ToastFields
import com.veygard.starwarssage.util.ToastTypes

fun getToastFields(toastType: ToastTypes?, context: Context?): ToastFields? {
    var toastFields: ToastFields? = null
    context?.let {
        toastFields= ToastFields()
        toastFields!!.apply {
            when (toastType) {
                ToastTypes.ConnectionError, ToastTypes.ServerError -> {
                    text = context.getString(R.string.snackbar_error_message)
                    background = context.getColor(R.color.error)
                    textColor = context.getColor(R.color.white)
                }
                is ToastTypes.DownloadPeople -> {
                    text = context.getString(
                        R.string.snackbar_download_message,
                        toastType.current,
                        toastType.all
                    )
                    background = context.getColor(R.color.blue)
                    textColor = context.getColor(R.color.white)
                }
                ToastTypes.DownloadMovie -> {
                    text = context.getString(R.string.snackbar_download_movies_message)
                    background = context.getColor(R.color.toast_back)
                    textColor = context.getColor(R.color.shimmer_placeholder)
                }
                ToastTypes.NoMoviesLeft -> {
                    text = context.getString(R.string.snackbar_no_movies_message)
                    background = context.getColor(R.color.background_night)
                    textColor = context.getColor(R.color.white)
                }

            }

        }

    }
    return toastFields
}