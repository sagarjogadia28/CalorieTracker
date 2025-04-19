package com.sagarjogadia28.tracker_presentation.tracker_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagarjogadia28.core.domain.preferences.UserInfoPreferences
import com.sagarjogadia28.core.navigation.Route
import com.sagarjogadia28.core.util.UiEvent
import com.sagarjogadia28.tracker_domain.usecase.TrackerUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TrackerOverviewViewModel(
    preferences: UserInfoPreferences,
    private val trackerUseCases: TrackerUseCases
) : ViewModel() {

    init {
        refreshFoods()
        viewModelScope.launch {
            preferences.saveShouldShowOnboarding(false)
        }
    }

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    var uiState by mutableStateOf(TrackerOverviewState())
        private set

    private var getFoodsForDateJob: Job? = null

    fun onEvent(event: TrackerOverviewEvent) {
        when (event) {
            is TrackerOverviewEvent.OnAddMealClick -> {
                viewModelScope.launch {
                    _uiChannel.send(
                        UiEvent.Navigate(
                            route = Route.Search(
                                mealName = event.meal.mealType.name,
                                dayOfMonth = uiState.date.dayOfMonth,
                                monthValue = uiState.date.monthValue,
                                year = uiState.date.year
                            )
                        )
                    )
                }
            }

            is TrackerOverviewEvent.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    trackerUseCases.deleteTrackedFoodUseCase(event.trackedFood)
                    refreshFoods()
                }
            }

            TrackerOverviewEvent.OnNextDayClick -> {
                uiState = uiState.copy(
                    date = uiState.date.plusDays(1)
                )
                refreshFoods()
            }

            TrackerOverviewEvent.OnPrevDayClick -> {
                uiState = uiState.copy(
                    date = uiState.date.minusDays(1)
                )
                refreshFoods()
            }

            is TrackerOverviewEvent.OnToggleMealClick -> {
                uiState = uiState.copy(
                    meals = uiState.meals.map {
                        if (it.name == event.meal.name) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }
        }
    }

    private fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = trackerUseCases
            .getFoodsForDateUseCase(uiState.date)
            .onEach { listOfTrackedFood ->
                val nutrients = trackerUseCases.calculateMealNutrientsUseCase(listOfTrackedFood)
                uiState = uiState.copy(
                    totalCarbs = nutrients.totalCarbs,
                    totalProtein = nutrients.totalProtein,
                    totalFat = nutrients.totalFat,
                    totalCalories = nutrients.totalCalories,
                    carbsGoal = nutrients.carbsGoal,
                    proteinGoal = nutrients.proteinGoal,
                    fatGoal = nutrients.fatGoal,
                    caloriesGoal = nutrients.caloriesGoal,
                    trackedFoods = listOfTrackedFood,
                    meals = uiState.meals.map { meal ->
                        val nutrientsForMeal =
                            nutrients.mealNutrients[meal.mealType] ?: return@map meal.copy(
                                carbs = 0,
                                protein = 0,
                                fat = 0,
                                calories = 0
                            )
                        meal.copy(
                            carbs = nutrientsForMeal.carbs,
                            protein = nutrientsForMeal.protein,
                            fat = nutrientsForMeal.fat,
                            calories = nutrientsForMeal.calories
                        )
                    }
                )
            }.launchIn(viewModelScope)
    }
}