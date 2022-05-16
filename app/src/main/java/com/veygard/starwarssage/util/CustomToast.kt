package com.veygard.starwarssage.util

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.veygard.starwarssage.R


class CustomToast(
    var context: Context,
    var message: String,
    var bgColor: Int = context.getColor(R.color.blue),
    var textColor: Int = context.getColor(R.color.white),
    var dur: Int = LENGTH_SHORT,
) :
    Toast(context) {

    init {
        val view: View = View.inflate(context, R.layout.custom_toast, null)
        val txtMsg = view.findViewById<TextView>(R.id.custom_toast_text)
        val card = view.findViewById<CardView>(R.id.custom_toast_card)

        txtMsg.text = message
        txtMsg.setTextColor(textColor)
        card.background.setTint(bgColor)

        setView(view)
        duration = dur
    }
}