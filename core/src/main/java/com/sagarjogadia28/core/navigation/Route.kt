package com.sagarjogadia28.core.navigation

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
sealed class Route {
    @Serializable object Welcome : Route()
    @Serializable object Age: Route()
    @Serializable object Gender: Route()
    @Serializable object Height: Route()
    @Serializable object Weight: Route()
    @Serializable object NutrientGoal: Route()
    @Serializable object Activity: Route()
    @Serializable object Goal: Route()
    @Serializable object TrackerOverview: Route()
    @Serializable
    data class Search(
        val mealType: String,
        val dayOfMonth: Int,
        val monthValue: Int,
        val year: Int
    ) : Route()
}