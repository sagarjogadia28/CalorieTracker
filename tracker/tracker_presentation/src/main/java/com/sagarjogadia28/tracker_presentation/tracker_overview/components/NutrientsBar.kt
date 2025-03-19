package com.sagarjogadia28.tracker_presentation.tracker_overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sagarjogadia28.core_ui.ui.theme.CarbColor
import com.sagarjogadia28.core_ui.ui.theme.FatColor
import com.sagarjogadia28.core_ui.ui.theme.ProteinColor


@Composable
fun NutrientsBar(
    carbs: Int,
    protein: Int,
    fat: Int,
    calories: Int,
    calorieGoal: Int,
    modifier: Modifier = Modifier
) {
    val background = MaterialTheme.colorScheme.background
    val caloriesExceedColor = MaterialTheme.colorScheme.error
    val carbWidthRatio = remember {
        Animatable(0f)
    }
    val proteinWidthRatio = remember {
        Animatable(0f)
    }
    val fatWidthRatio = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = carbs) {
        carbWidthRatio.animateTo(
            targetValue = calculateWidthRatio(carbs, 4f, calorieGoal)
        )
    }
    LaunchedEffect(key1 = protein) {
        proteinWidthRatio.animateTo(
            targetValue = calculateWidthRatio(protein, 4f, calorieGoal)
        )
    }
    LaunchedEffect(key1 = fat) {
        fatWidthRatio.animateTo(
            targetValue = calculateWidthRatio(fat, 9f, calorieGoal)
        )
    }
    Canvas(modifier = modifier) {
        if (calories <= calorieGoal) {
            val carbsWidth = carbWidthRatio.value * size.width
            val proteinWidth = proteinWidthRatio.value * size.width
            val fatWidth = fatWidthRatio.value * size.width

            drawNutrientBar(background, size.width, size.height)
            drawNutrientBar(FatColor, carbsWidth + proteinWidth + fatWidth, size.height)
            drawNutrientBar(ProteinColor, carbsWidth + proteinWidth, size.height)
            drawNutrientBar(CarbColor, carbsWidth, size.height)
        } else {
            drawNutrientBar(caloriesExceedColor, size.width, size.height)
        }
    }
}

private fun calculateWidthRatio(nutrientGrams: Int, kcalPerGram: Float, calorieGoal: Int): Float =
    (nutrientGrams * kcalPerGram) / calorieGoal

fun DrawScope.drawNutrientBar(color: Color, width: Float, height: Float) {
    drawRoundRect(
        color = color,
        size = Size(width, height),
        cornerRadius = CornerRadius(100f)
    )
}