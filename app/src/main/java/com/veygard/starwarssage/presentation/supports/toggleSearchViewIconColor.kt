package com.veygard.starwarssage.presentation.supports

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import com.veygard.starwarssage.R

fun toggleSearchViewIconColor(isNotEmpty: Boolean, context: Context?, icon: ImageView) {

    if (isNotEmpty) icon.setColorFilter(
        context?.getColor(R.color.blue) ?: Color.BLACK
    )
    else icon.setColorFilter(
        context?.getColor(R.color.grey) ?: Color.LTGRAY
    )
}