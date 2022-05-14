package com.veygard.starwarssage.util

import android.view.View

 fun toggleVisibility(gone: Boolean, view: View?) {
    if (gone) view?.visibility = View.GONE
    else view?.visibility = View.VISIBLE
}