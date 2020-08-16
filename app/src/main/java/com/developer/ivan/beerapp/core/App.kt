package com.developer.ivan.beerapp.core

import android.app.Application
import com.developer.ivan.beerapp.di.AppComponent
import com.developer.ivan.beerapp.di.DaggerAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

class App : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        component = getAppComponent()
        super.onCreate()
    }


    open fun getAppComponent(): AppComponent =
        DaggerAppComponent
            .factory()
            .create(this)
}