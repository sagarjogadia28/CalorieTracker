package com.sagarjogadia28.calorietracker.di

import com.sagarjogadia28.core.usecase.FilterOutDigitsUseCase
import com.sagarjogadia28.onboarding_presentation.age.AgeViewModel
import com.sagarjogadia28.onboarding_presentation.gender.GenderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    single {
        FilterOutDigitsUseCase()
    }

    viewModel {
        GenderViewModel(get())
    }

    viewModel {
        AgeViewModel(
            preferences = get(),
            filterOutDigitsUseCase = get()
        )
    }
}