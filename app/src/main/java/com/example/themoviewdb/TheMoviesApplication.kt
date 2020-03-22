package com.example.themoviewdb

import android.app.Application
import org.koin.android.ext.android.startKoin

class TheMoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(
            this,
            listOf(applicationModule, networkModule, viewModelModule)
        )
    }
}