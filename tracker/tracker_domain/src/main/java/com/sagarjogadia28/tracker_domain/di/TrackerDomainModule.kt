package com.sagarjogadia28.tracker_domain.di

import com.sagarjogadia28.tracker_domain.usecase.CalculateMealNutrientsUseCase
import com.sagarjogadia28.tracker_domain.usecase.DeleteTrackedFoodUseCase
import com.sagarjogadia28.tracker_domain.usecase.GetFoodsForDateUseCase
import com.sagarjogadia28.tracker_domain.usecase.SearchFoodUseCase
import com.sagarjogadia28.tracker_domain.usecase.TrackerUseCases
import com.sagarjogadia28.tracker_domain.usecase.UpsertTrackedFoodUseCase
import org.koin.dsl.module

val trackerDomainModule = module {
    single {
        CalculateMealNutrientsUseCase(get())
    }

    single {
        DeleteTrackedFoodUseCase(get())
    }

    single {
        GetFoodsForDateUseCase(get())
    }

    single {
        SearchFoodUseCase(get())
    }

    single {
        TrackerUseCases(get(), get(), get(), get(), get())
    }

    single {
        UpsertTrackedFoodUseCase(get())
    }

}