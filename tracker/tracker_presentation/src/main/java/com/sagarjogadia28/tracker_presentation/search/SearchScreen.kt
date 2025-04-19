package com.sagarjogadia28.tracker_presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sagarjogadia28.core.R
import com.sagarjogadia28.core.util.UiEvent
import com.sagarjogadia28.core_ui.LocalSpacing
import com.sagarjogadia28.tracker_domain.model.MealType
import com.sagarjogadia28.tracker_presentation.search.components.SearchTextField
import com.sagarjogadia28.tracker_presentation.search.components.TrackableFoodItem
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun SearchScreen(
    snackBarHostState: SnackbarHostState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = koinViewModel()
) {
    val state = viewModel.uiState
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        event.message.asString(context)
                    )
                    keyboardController?.hide()
                }

                UiEvent.NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Text(
            text = stringResource(R.string.add_meal, mealName),
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SearchTextField(
            text = state.query,
            shouldShowHint = state.isHintVisible,
            onValueChange = { viewModel.onEvent(SearchEvent.OnQueryChange(it)) },
            onSearch = {
                keyboardController?.hide()
                viewModel.onEvent(SearchEvent.OnSearch)
                       },
            onFocusChanged = { viewModel.onEvent(SearchEvent.OnSearchFocusChange(it.isFocused)) }
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.listOfTrackableFoods) { food ->
                TrackableFoodItem(
                    trackableFoodUiState = food,
                    onClick = {
                        viewModel.onEvent(SearchEvent.OnToggleTrackableFood(food.trackableFood))
                    },
                    onAmountChange = {
                        viewModel.onEvent(
                            SearchEvent.OnAmountForFoodChange(
                                trackableFood = food.trackableFood,
                                amount = it
                            )
                        )
                    },
                    onTrack = {
                        keyboardController?.hide()
                        viewModel.onEvent(
                            SearchEvent.OnTrackFoodClick(
                                trackableFood = food.trackableFood,
                                mealType = MealType.fromString(mealName),
                                date = LocalDate.of(year, month, dayOfMonth)
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isSearching -> CircularProgressIndicator()
            state.listOfTrackableFoods.isEmpty() -> {
                Text(
                    text = stringResource(id = R.string.no_results),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}