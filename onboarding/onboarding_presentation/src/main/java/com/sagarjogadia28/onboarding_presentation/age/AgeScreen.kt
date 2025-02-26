package com.sagarjogadia28.onboarding_presentation.age

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
import com.sagarjogadia28.core.usecase.FilterOutDigitsUseCase
import com.sagarjogadia28.core.util.UiEvent
import com.sagarjogadia28.core_ui.LocalSpacing
import com.sagarjogadia28.core_ui.ui.theme.CalorieTrackerTheme
import com.sagarjogadia28.onboarding_presentation.components.ActionButton
import com.sagarjogadia28.onboarding_presentation.components.UnitTextField
import com.sagarjogadia28.onboarding_presentation.mock.FakePreferences
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun AgeScreen(
    snackBarHostState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AgeViewModel = koinViewModel()
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiChannel.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }

                else -> {}
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.whats_your_age),
                style = MaterialTheme.typography.displaySmall
            )
            UnitTextField(
                value = viewModel.age,
                onValueChange = viewModel::onAgeUpdated,
                unit = stringResource(R.string.years)
            )
        }
        ActionButton(
            text = R.string.next,
            onClick = viewModel::saveAge,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AgeScreenPreview() {
    CalorieTrackerTheme {
        AgeScreen(
            snackBarHostState = SnackbarHostState(),
            onNavigate = {},
            viewModel = AgeViewModel(FakePreferences(), FilterOutDigitsUseCase())
        )
    }
}