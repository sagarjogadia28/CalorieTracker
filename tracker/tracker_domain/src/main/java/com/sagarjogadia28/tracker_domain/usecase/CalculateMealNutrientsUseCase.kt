package com.sagarjogadia28.tracker_domain.usecase

import com.sagarjogadia28.core.domain.model.ActivityLevel
import com.sagarjogadia28.core.domain.model.Gender
import com.sagarjogadia28.core.domain.model.GoalType
import com.sagarjogadia28.core.domain.model.UserInfo
import com.sagarjogadia28.core.domain.preferences.UserInfoPreferences
import com.sagarjogadia28.tracker_domain.model.MealType
import com.sagarjogadia28.tracker_domain.model.TrackedFood
import kotlinx.coroutines.flow.first
import kotlin.math.roundToInt

class CalculateMealNutrientsUseCase(
    private val preferences: UserInfoPreferences
) {
    suspend operator fun invoke(trackedFoods: List<TrackedFood>): Result {
        val allNutrients = trackedFoods.groupBy {
            it.type
        }.mapValues { entry ->
            val (mealType, listOfTrackedFood) = entry
            MealNutrients(
                carbs = listOfTrackedFood.sumOf { it.carbs },
                protein = listOfTrackedFood.sumOf { it.protein },
                fat = listOfTrackedFood.sumOf { it.fat },
                calories = listOfTrackedFood.sumOf { it.calories },
                mealType = mealType
            )
        }

        val totalCarbs = allNutrients.values.sumOf { it.carbs }
        val totalProtein = allNutrients.values.sumOf { it.protein }
        val totalFat = allNutrients.values.sumOf { it.fat }
        val totalCalories = allNutrients.values.sumOf { it.calories }

        val userInfo = preferences.loadUserInfo().first()
        val caloriesGoal = dailyCalorieRequirement(userInfo)
        val carbsGoal = (caloriesGoal * userInfo.carbRatio / 4f).roundToInt()
        val proteinGoal = (caloriesGoal * userInfo.proteinRatio / 4f).roundToInt()
        val fatGoal = (caloriesGoal * userInfo.fatRatio / 9f).roundToInt()

        return Result(
            carbsGoal = carbsGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloriesGoal,
            totalCarbs = totalCarbs,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients
        )
    }

    data class MealNutrients(
        val carbs: Int,
        val protein: Int,
        val fat: Int,
        val calories: Int,
        val mealType: MealType
    )

    data class Result(
        val carbsGoal: Int,
        val proteinGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val mealNutrients: Map<MealType, MealNutrients>
    )

    private fun dailyCalorieRequirement(userInfo: UserInfo): Int {
        val activityFactor = when (userInfo.activityLevel) {
            ActivityLevel.LOW -> 1.2f
            ActivityLevel.MEDIUM -> 1.3f
            ActivityLevel.HIGH -> 1.4f
        }
        val calorieExtra = when (userInfo.goalType) {
            GoalType.LOSE -> -500
            GoalType.KEEP -> 0
            GoalType.GAIN -> 500
        }
        return (bmr(userInfo) * activityFactor + calorieExtra).roundToInt()
    }

    private fun bmr(userInfo: UserInfo): Int {
        with(userInfo) {
            return when (gender) {
                Gender.MALE -> {
                    (66.47f + 13.75f * weight + 5f * height - 6.75f * age).roundToInt()
                }

                Gender.FEMALE -> {
                    (665.09f + 9.56f * weight + 1.84f * height - 4.67f * age).roundToInt()
                }
            }
        }
    }

}