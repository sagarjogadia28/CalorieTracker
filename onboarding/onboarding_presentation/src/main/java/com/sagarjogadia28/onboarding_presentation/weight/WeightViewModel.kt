package com.sagarjogadia28.onboarding_presentation.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagarjogadia28.core.R
import com.sagarjogadia28.core.domain.preferences.UserInfoPreferences
import com.sagarjogadia28.core.navigation.Route
import com.sagarjogadia28.core.usecase.FilterOutWeightUseCase
import com.sagarjogadia28.core.util.UiEvent
import com.sagarjogadia28.core.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class WeightViewModel(
    private val preferences: UserInfoPreferences,
    private val filterOutWeightUseCase: FilterOutWeightUseCase
) : ViewModel() {
    var weight by mutableStateOf("80.0")
        private set

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    fun updateWeight(weight: String) {
        if (weight.length <= 5)
            this.weight = filterOutWeightUseCase(weight)
    }

    fun saveWeight() {
        viewModelScope.launch {
            val weightNumber = weight.toFloatOrNull()
            if (weightNumber == null || weightNumber == 0f) {
                _uiChannel.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_weight_cant_be_empty)))
                return@launch
            }
            preferences.saveWeight(weightNumber)
            _uiChannel.send(UiEvent.Navigate)
        }
    }
}