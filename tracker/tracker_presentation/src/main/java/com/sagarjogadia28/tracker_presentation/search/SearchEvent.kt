package com.sagarjogadia28.tracker_presentation.search

import com.sagarjogadia28.tracker_domain.model.MealType
import com.sagarjogadia28.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchEvent {
    data object OnSearch : SearchEvent()
    data class OnQueryChange(val query: String) : SearchEvent()
    data class OnToggleTrackableFood(val trackableFood: TrackableFood) : SearchEvent()
    data class OnAmountForFoodChange(
        val trackableFood: TrackableFood, val amount: String
    ) : SearchEvent()
    data class OnTrackFoodClick(
        val trackableFood: TrackableFood, val mealType: MealType, val date: LocalDate
    ) : SearchEvent()
    data class OnSearchFocusChange(val isFocused: Boolean) : SearchEvent()
}