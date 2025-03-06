package com.sagarjogadia28.tracker_domain.usecase

import com.sagarjogadia28.tracker_domain.model.MealType
import com.sagarjogadia28.tracker_domain.model.TrackableFood
import com.sagarjogadia28.tracker_domain.model.TrackedFood
import com.sagarjogadia28.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class UpsertTrackedFoodUseCase(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(
        trackableFood: TrackableFood,
        mealType: MealType,
        amount: Int,
        date: LocalDate
    ) {
        repository.upsertTrackedFood(
            TrackedFood(
                name = trackableFood.productName,
                carbs = calculateNutrient(trackableFood.carbohydrates100g, amount),
                protein = calculateNutrient(trackableFood.proteins100g, amount),
                fat = calculateNutrient(trackableFood.fat100g, amount),
                imageUrl = trackableFood.imageFrontThumbUrl,
                calories = ((trackableFood.energyKcal100g / 100f) * amount).roundToInt(),
                type = mealType,
                amount = amount,
                date = date
            )
        )
    }

    private fun calculateNutrient(nutrients100g: Int, amount: Int) =
        ((nutrients100g / 100f) * amount).roundToInt()
}