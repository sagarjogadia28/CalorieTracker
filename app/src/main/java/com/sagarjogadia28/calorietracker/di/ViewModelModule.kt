package com.sagarjogadia28.calorietracker.di

import com.sagarjogadia28.core.usecase.FilterOutDigitsUseCase
import com.sagarjogadia28.core.usecase.FilterOutWeightUseCase
import com.sagarjogadia28.onboarding_presentation.activity.ActivityLevelViewModel
import com.sagarjogadia28.onboarding_presentation.age.AgeViewModel
import com.sagarjogadia28.onboarding_presentation.gender.GenderViewModel
import com.sagarjogadia28.onboarding_presentation.goal.GoalTypeViewModel
import com.sagarjogadia28.onboarding_presentation.height.HeightViewModel
import com.sagarjogadia28.onboarding_presentation.weight.WeightViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    single {
        FilterOutDigitsUseCase()
    }

    single {
        FilterOutWeightUseCase()
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

    viewModel {
        HeightViewModel(
            preferences = get(),
            filterOutDigitsUseCase = get()
        )
    }

    viewModel {
        WeightViewModel(
            preferences = get(),
            filterOutWeightUseCase = get()
        )
    }

    viewModel {
        GoalTypeViewModel(get())
    }

    viewModel {
        ActivityLevelViewModel(get())
    }
}