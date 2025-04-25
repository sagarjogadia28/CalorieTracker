package com.sagarjogadia28.onboarding_presentation.nutrient_goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sagarjogadia28.core.R
import com.sagarjogadia28.core.util.UiEvent
import com.sagarjogadia28.core_ui.LocalSpacing
import com.sagarjogadia28.core_ui.ui.theme.CalorieTrackerTheme
import com.sagarjogadia28.onboarding_presentation.components.ActionButton
import com.sagarjogadia28.onboarding_presentation.components.UnitTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun NutrientGoalScreen(
    snackBarHostState: SnackbarHostState,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NutrientGoalViewModel = koinViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.uiChannel.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNextClick()
                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message.asString(context))
                }

                else -> {}
            }
        }
    }

    NutrientGoalContent(
        carbs = viewModel.uiState.carbs,
        protein = viewModel.uiState.protein,
        fat = viewModel.uiState.fat,
        event = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
fun NutrientGoalContent(
    carbs: String,
    protein: String,
    fat: String,
    event: (NutrientGoalEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            Text(
                text = stringResource(R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.displaySmall
            )
            UnitTextField(
                value = carbs,
                onValueChange = {
                    event(NutrientGoalEvent.OnCarbRatioEnter(it))
                },
                unit = stringResource(R.string.carbs)
            )
            UnitTextField(
                value = protein,
                onValueChange = {
                    event(NutrientGoalEvent.OnProteinRatioEnter(it))
                },
                unit = stringResource(R.string.protein)
            )
            UnitTextField(
                value = fat,
                onValueChange = {
                    event(NutrientGoalEvent.OnFatRatioEnter(it))
                },
                unit = stringResource(R.string.fat)
            )
        }
        ActionButton(
            text = R.string.next,
            onClick = {
                event(NutrientGoalEvent.OnNextClick)
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NutrientGoalScreenPreview() {
    CalorieTrackerTheme {
        NutrientGoalContent(
            carbs = "40",
            protein = "30",
            fat = "30",
            event = {}
        )
    }
}