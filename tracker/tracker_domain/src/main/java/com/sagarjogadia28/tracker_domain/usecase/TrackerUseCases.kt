package com.sagarjogadia28.tracker_domain.usecase

data class TrackerUseCases(
    val calculateMealNutrientsUseCase: CalculateMealNutrientsUseCase,
    val deleteTrackedFoodUseCase: DeleteTrackedFoodUseCase,
    val getFoodsForDateUseCase: GetFoodsForDateUseCase,
    val searchFoodUseCase: SearchFoodUseCase,
    val upsertTrackedFoodUseCase: UpsertTrackedFoodUseCase
)