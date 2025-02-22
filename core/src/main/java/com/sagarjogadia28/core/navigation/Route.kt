package com.sagarjogadia28.core.navigation

import kotlinx.serialization.Serializable

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
    @Serializable object Search: Route()
}