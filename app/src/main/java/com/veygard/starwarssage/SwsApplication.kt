package com.veygard.starwarssage

import android.app.Application
import com.veygard.starwarssage.di.AppComponent
import com.veygard.starwarssage.di.DaggerAppComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SwsApplication: Application(){

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: SwsApplication
    }
}