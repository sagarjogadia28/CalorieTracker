package com.sagarjogadia28.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagarjogadia28.core.R
import com.sagarjogadia28.core.usecase.FilterOutDigitsUseCase
import com.sagarjogadia28.core.util.UiEvent
import com.sagarjogadia28.core.util.UiText
import com.sagarjogadia28.tracker_domain.usecase.TrackerUseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase
) : ViewModel() {

    var uiState by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    var uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChange -> {
                uiState = uiState.copy(query = event.query)
            }

            is SearchEvent.OnAmountForFoodChange -> {
                uiState = uiState.copy(
                    listOfTrackableFoods = uiState.listOfTrackableFoods.map {
                        if (it.trackableFood == event.trackableFood) {
                            it.copy(amount = filterOutDigitsUseCase(event.amount))
                        } else it
                    })
            }

            SearchEvent.OnSearch -> executeSearch()


            is SearchEvent.OnSearchFocusChange -> {
                uiState = uiState.copy(
                    isHintVisible = uiState.query.isBlank() && !event.isFocused
                )
            }

            is SearchEvent.OnToggleTrackableFood -> {
                uiState = uiState.copy(
                    listOfTrackableFoods = uiState.listOfTrackableFoods.map {
                        if (it.trackableFood == event.trackableFood) it.copy(
                            isExpanded = !it.isExpanded
                        ) else it
                    })
            }

            is SearchEvent.OnTrackFoodClick -> trackFood(event)
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            uiState = uiState.copy(isSearching = true, listOfTrackableFoods = emptyList())
            trackerUseCases
                .searchFoodUseCase(uiState.query)
                .onSuccess { listOfTrackableFoods ->
                    uiState = uiState.copy(
                        listOfTrackableFoods = listOfTrackableFoods.map {
                            TrackableFoodUiState(it)
                        },
                        isSearching = false,
                        query = ""
                    )
                }
                .onFailure {
                    uiState = uiState.copy(isSearching = false)
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_something_went_wrong)))
                }
        }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodClick) {
        viewModelScope.launch {
            val food = uiState.listOfTrackableFoods.find { it.trackableFood == event.trackableFood }
            trackerUseCases.upsertTrackedFoodUseCase(
                trackableFood = food?.trackableFood ?: return@launch,
                amount = food.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }

}