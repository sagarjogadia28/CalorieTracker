package com.sagarjogadia28.onboarding_presentation.activity

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
import com.sagarjogadia28.core.util.UiEvent
import com.sagarjogadia28.core_ui.LocalSpacing
import com.sagarjogadia28.core_ui.ui.theme.CalorieTrackerTheme
import com.sagarjogadia28.onboarding_presentation.components.ActionButton
import com.sagarjogadia28.onboarding_presentation.components.SelectableButton
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActivityLevelScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ActivityLevelViewModel = koinViewModel()
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

    ActivityLevelScreen(
        selectedActivityLevel = viewModel.activityLevel,
        onActivityLevelSelected = viewModel::onActivityLevelSelected,
        onClick = viewModel::saveActivityLevel,
        modifier = modifier
    )
}

@Composable
fun ActivityLevelScreen(
    selectedActivityLevel: ActivityLevel,
    onActivityLevelSelected: (ActivityLevel) -> Unit,
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
                text = stringResource(R.string.whats_your_activity_level),
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
            ) {
                ActivityLevel.entries.map { activityLevel ->
                    SelectableButton(
                        text = when (activityLevel) {
                            ActivityLevel.LOW -> R.string.low
                            ActivityLevel.MEDIUM -> R.string.medium
                            ActivityLevel.HIGH -> R.string.high
                        },
                        onClick = { onActivityLevelSelected(activityLevel) },
                        isSelected = selectedActivityLevel == activityLevel,
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
private fun ActivityLevelScreenPreview() {
    CalorieTrackerTheme {
        ActivityLevelScreen(
            selectedActivityLevel = ActivityLevel.LOW,
            onActivityLevelSelected = {},
            onClick = {},
        )
    }
}