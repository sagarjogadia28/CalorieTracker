package com.sagarjogadia28.onboarding_presentation.nutrient_goal

sealed class NutrientGoalEvent {
    data class OnCarbRatioEnter(val carbRatio: String) : NutrientGoalEvent()
    data class OnProteinRatioEnter(val proteinRatio: String) : NutrientGoalEvent()
    data class OnFatRatioEnter(val fatRatio: String) : NutrientGoalEvent()
    data object OnNextClick : NutrientGoalEvent()
}