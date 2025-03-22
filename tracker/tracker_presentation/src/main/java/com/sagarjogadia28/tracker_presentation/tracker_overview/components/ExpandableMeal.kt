package com.sagarjogadia28.tracker_presentation.tracker_overview.components

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.sagarjogadia28.core.R
import com.sagarjogadia28.core.util.UiText
import com.sagarjogadia28.core_ui.Dimensions
import com.sagarjogadia28.core_ui.LocalSpacing
import com.sagarjogadia28.core_ui.ui.theme.CalorieTrackerTheme
import com.sagarjogadia28.tracker_domain.model.MealType
import com.sagarjogadia28.tracker_presentation.components.NutrientInfo
import com.sagarjogadia28.tracker_presentation.components.UnitDisplay
import com.sagarjogadia28.tracker_presentation.tracker_overview.Meal

@Composable
fun ExpandableMeal(
    meal: Meal,
    onToggleClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    Column(modifier = modifier) {
        MealHeaderRow(meal, context, onToggleClick, spacing)
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        AnimatedVisibility(visible = meal.isExpanded) {
            content()
        }
    }
}

@Composable
private fun MealHeaderRow(
    meal: Meal,
    context: Context,
    onToggleClick: () -> Unit,
    spacing: Dimensions
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggleClick)
            .padding(spacing.spaceMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = meal.drawableRes),
            contentDescription = meal.name.asString(context)
        )
        Spacer(modifier = Modifier.width(spacing.spaceMedium))

        Column(modifier = Modifier.weight(1f)) {
            MealTitleRow(meal, context)
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            MealNutrientsRow(meal, spacing)
        }
    }
}

@Composable
private fun MealTitleRow(meal: Meal, context: Context) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = meal.name.asString(context),
            style = MaterialTheme.typography.displaySmall
        )
        Icon(
            imageVector = if (meal.isExpanded) Icons.Default.KeyboardArrowUp
            else Icons.Default.KeyboardArrowDown,
            contentDescription = stringResource(
                id = if (meal.isExpanded) R.string.collapse else R.string.extend
            )
        )
    }
}

@Composable
private fun MealNutrientsRow(meal: Meal, spacing: Dimensions) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        UnitDisplay(
            amount = meal.calories,
            unit = stringResource(id = R.string.kcal),
            amountTextSize = 30.sp
        )
        Row {
            NutrientInfo(
                name = stringResource(id = R.string.carbs),
                amount = meal.carbs,
                unit = stringResource(id = R.string.grams)
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            NutrientInfo(
                name = stringResource(id = R.string.protein),
                amount = meal.protein,
                unit = stringResource(id = R.string.grams)
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            NutrientInfo(
                name = stringResource(id = R.string.fat),
                amount = meal.fat,
                unit = stringResource(id = R.string.grams)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpandableMealPreview() {
    CalorieTrackerTheme {
        ExpandableMeal(
            meal = Meal(
                name = UiText.StringResource(R.string.lunch),
                drawableRes = R.drawable.ic_lunch,
                mealType = MealType.Dinner,
                carbs = 30,
                protein = 10,
                fat = 20,
                calories = 2223,
                isExpanded = false
            ),
            onToggleClick = {},
            content = {}
        )
    }
}