package com.sagarjogadia28.onboarding_presentation.goal

import com.sagarjogadia28.onboarding_presentation.activity.ActivityLevelViewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.sagarjogadia28.core.R
import com.sagarjogadia28.core.domain.model.ActivityLevel
import com.sagarjogadia28.core.domain.model.GoalType
import com.sagarjogadia28.core.util.UiEvent
import com.sagarjogadia28.core_ui.LocalSpacing
import com.sagarjogadia28.core_ui.ui.theme.CalorieTrackerTheme
import com.sagarjogadia28.onboarding_presentation.activity.ActivityLevelScreen
import com.sagarjogadia28.onboarding_presentation.components.ActionButton
import com.sagarjogadia28.onboarding_presentation.components.SelectableButton
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun GoalTypeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GoalTypeViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.uiChannel.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }

                else -> Unit
            }
        }
    }

    GoalTypeScreen(
        selectedGoalType = viewModel.goalType,
        onGoalTypeSelected = viewModel::onGoalTypeSelected,
        onClick = viewModel::saveGoalType,
        modifier = modifier
    )
}

@Composable
fun GoalTypeScreen(
    selectedGoalType: GoalType,
    onGoalTypeSelected: (GoalType) -> Unit,
    onClick: () -> Unit,
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.your_goal),
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
            ) {
                GoalType.entries.map { goalType ->
                    SelectableButton(
                        text = when (goalType) {
                            GoalType.LOSE -> R.string.lose
                            GoalType.KEEP -> R.string.keep
                            GoalType.GAIN -> R.string.gain
                        },
                        onClick = { onGoalTypeSelected(goalType) },
                        isSelected = selectedGoalType == goalType,
                        color = MaterialTheme.colorScheme.primary,
                        selectedTextColor = Color.White,
                        textStyle = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
        ActionButton(
            text = R.string.next,
            onClick = onClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun GoalTypeScreenPreview() {
    CalorieTrackerTheme {
        GoalTypeScreen(
            selectedGoalType = GoalType.LOSE,
            onGoalTypeSelected = {},
            onClick = {},
        )
    }
}