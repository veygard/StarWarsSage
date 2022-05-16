package com.veygard.starwarssage.presentation.widgets

import android.content.Context
import android.graphics.Color
import android.util.Log
import com.veygard.starwarssage.R
import com.veygard.starwarssage.util.CustomToast
import com.veygard.starwarssage.util.ToastFields
import com.veygard.starwarssage.util.ToastTypes

fun getToastFields(toastType: ToastTypes?, context: Context?): ToastFields? {
    var toastFields: ToastFields? = null
    context?.let {
        toastFields= ToastFields()

        when (toastType) {
            ToastTypes.ConnectionError, ToastTypes.ServerError -> {
                toastFields!!.text = context.getString(R.string.snackbar_error_message)
                toastFields!!.background = context.getColor(R.color.error)
                toastFields!!.textColor = context.getColor(R.color.white)
            }
            is ToastTypes.Download -> {
                toastFields!!.text = context.getString(
                    R.string.snackbar_download_message,
                    toastType.current,
                    toastType.all
                )
                toastFields!!.background = context.getColor(R.color.blue)
                toastFields!!.textColor = context.getColor(R.color.white)
            }

            ToastTypes.NoMoviesLeft -> {
                toastFields!!.text = context.getString(R.string.snackbar_no_movies_message)
                toastFields!!.background = context.getColor(R.color.background_night)
                toastFields!!.textColor = context.getColor(R.color.white)
            }

        }

    }
    return toastFields
}