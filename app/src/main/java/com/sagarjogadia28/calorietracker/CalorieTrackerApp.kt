package com.sagarjogadia28.calorietracker

import android.app.Application
import com.sagarjogadia28.calorietracker.di.dataStoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CalorieTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CalorieTrackerApp)
            modules(dataStoreModule)
        }
    }
}