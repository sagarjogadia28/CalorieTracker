package com.sagarjogadia28.calorietracker

import android.app.Application
import com.sagarjogadia28.calorietracker.di.dataStoreModule
import com.sagarjogadia28.calorietracker.di.viewModelModule
import com.sagarjogadia28.tracker_data.di.trackerDataModule
import com.sagarjogadia28.tracker_domain.di.trackerDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CalorieTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CalorieTrackerApp)
            modules(
                dataStoreModule,
                viewModelModule,
                trackerDataModule,
                trackerDomainModule
            )
        }
    }
}