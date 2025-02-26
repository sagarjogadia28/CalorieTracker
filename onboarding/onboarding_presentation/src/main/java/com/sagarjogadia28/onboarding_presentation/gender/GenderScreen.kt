package com.sagarjogadia28.onboarding_presentation.gender

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
import com.sagarjogadia28.core.R
import com.sagarjogadia28.core.domain.model.Gender
import com.sagarjogadia28.core.util.UiEvent
import com.sagarjogadia28.core_ui.LocalSpacing
import com.sagarjogadia28.onboarding_presentation.components.ActionButton
import com.sagarjogadia28.onboarding_presentation.components.SelectableButton
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun GenderScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GenderViewModel = koinViewModel()
) {
    val spacing = LocalSpacing.current
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(R.string.whats_your_gender),
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
            ) {
                Gender.entries.map { gender ->
                    SelectableButton(
                        text = if (gender == Gender.MALE) R.string.male else R.string.female,
                        onClick = { viewModel.onGenderSelected(gender) },
                        isSelected = viewModel.gender == gender,
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
            onClick = viewModel::saveGender,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}