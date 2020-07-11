package com.emami.moviedb.app

import android.app.Application
import com.emami.moviedb.app.di.AppComponent
import com.emami.moviedb.app.di.DaggerAppComponent
import timber.log.Timber

class MovieDbApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder().application(this).build()
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}

