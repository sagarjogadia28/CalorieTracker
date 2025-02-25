package com.sagarjogadia28.calorietracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.sagarjogadia28.core.data.preferences.DefaultPreferences
import com.sagarjogadia28.core.domain.preferences.UserInfoPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_info_prefs")

val dataStoreModule = module {
    single {
        androidContext().dataStore
    }

    single<UserInfoPreferences> {
        DefaultPreferences(get())
    }
}