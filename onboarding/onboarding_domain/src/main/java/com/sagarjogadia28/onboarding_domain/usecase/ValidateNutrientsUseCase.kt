package com.sagarjogadia28.onboarding_domain.usecase

import com.sagarjogadia28.core.R
import com.sagarjogadia28.core.util.UiText

class ValidateNutrientsUseCase {
    operator fun invoke(
        carbsString: String,
        proteinString: String,
        fatString: String
    ): Result {

        val carbs = carbsString.toFloatOrNull()
        val protein = proteinString.toFloatOrNull()
        val fat = fatString.toFloatOrNull()

        if (carbs == null || protein == null || fat == null)
            return Result.Error(UiText.StringResource(R.string.error_invalid_values))

        if (carbs + protein + fat != 100f) {
            return Result.Error(UiText.StringResource(R.string.error_not_100_percent))
        }

        return Result.Success(carbs / 100f, protein / 100f, fat / 100f)
    }
}

sealed class Result {
    data class Success(
        val carbsRatio: Float,
        val proteinRatio: Float,
        val fatRatio: Float
    ) : Result()

    data class Error(val message: UiText) : Result()
}