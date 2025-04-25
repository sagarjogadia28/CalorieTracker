package com.sagarjogadia28.tracker_data.di

import android.app.Application
import androidx.room.Room
import com.sagarjogadia28.tracker_data.local.TrackerDatabase
import com.sagarjogadia28.tracker_data.remote.OpenFoodApi
import com.sagarjogadia28.tracker_data.repository.TrackerRepositoryImpl
import com.sagarjogadia28.tracker_domain.repository.TrackerRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val BASE_URL = "https://ca.openfoodfacts.org/"

val trackerDataModule = module {
    single {
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get()).build().create(OpenFoodApi::class.java)
    }

    single {
        Room.databaseBuilder(
            get<Application>(), TrackerDatabase::class.java, "tracker_db"
        ).fallbackToDestructiveMigration().build()
    }

    single {
        get<TrackerDatabase>().trackerDao
    }

    single<TrackerRepository> {
        TrackerRepositoryImpl(
            dao = get(),
            api = get()
        )
    }
}