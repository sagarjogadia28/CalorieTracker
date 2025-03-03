package com.sagarjogadia28.onboarding_presentation.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagarjogadia28.core.domain.preferences.UserInfoPreferences
import com.sagarjogadia28.core.navigation.Route
import com.sagarjogadia28.core.usecase.FilterOutDigitsUseCase
import com.sagarjogadia28.core.util.UiEvent
import com.sagarjogadia28.onboarding_domain.usecase.Result
import com.sagarjogadia28.onboarding_domain.usecase.ValidateNutrientsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NutrientGoalViewModel(
    private val preferences: UserInfoPreferences,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase,
    private val validateNutrientsUseCase: ValidateNutrientsUseCase
) : ViewModel() {

    var uiState by mutableStateOf(NutrientGoalState())
        private set

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbRatioEnter -> {
                if (event.carbRatio.length <= 3)
                    uiState = uiState.copy(
                        carbs = filterOutDigitsUseCase(event.carbRatio)
                    )
            }

            is NutrientGoalEvent.OnProteinRatioEnter -> {
                if (event.proteinRatio.length <= 3)
                    uiState = uiState.copy(
                        protein = filterOutDigitsUseCase(event.proteinRatio)
                    )
            }

            is NutrientGoalEvent.OnFatRatioEnter -> {
                if (event.fatRatio.length <= 3)
                    uiState = uiState.copy(
                        fat = filterOutDigitsUseCase(event.fatRatio)
                    )
            }

            NutrientGoalEvent.OnNextClick -> {

                val result = validateNutrientsUseCase(
                    carbsString = uiState.carbs,
                    proteinString = uiState.protein,
                    fatString = uiState.fat
                )
                viewModelScope.launch {
                    when (result) {
                        is Result.Error -> {
                            _uiChannel.send(UiEvent.ShowSnackBar(result.message))
                        }

                        is Result.Success -> {
                            preferences.saveCarbRatio(result.carbsRatio)
                            preferences.saveProteinRatio(result.proteinRatio)
                            preferences.saveFatRatio(result.fatRatio)
                            _uiChannel.send(UiEvent.Navigate(Route.TrackerOverview))
                        }
                    }
                }
            }
        }
    }
}