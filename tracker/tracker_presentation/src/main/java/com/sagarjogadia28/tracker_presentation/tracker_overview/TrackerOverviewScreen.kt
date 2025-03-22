package com.sagarjogadia28.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sagarjogadia28.core.util.UiEvent
import com.sagarjogadia28.core_ui.LocalSpacing
import com.sagarjogadia28.tracker_presentation.tracker_overview.components.NutrientsHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TrackerOverviewViewModel = koinViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.uiState
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item {
            NutrientsHeader(state)
        }
    }
}