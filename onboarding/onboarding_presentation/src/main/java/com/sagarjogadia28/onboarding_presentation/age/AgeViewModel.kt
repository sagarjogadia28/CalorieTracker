package com.sagarjogadia28.onboarding_presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagarjogadia28.core.R
import com.sagarjogadia28.core.domain.preferences.UserInfoPreferences
import com.sagarjogadia28.core.navigation.Route
import com.sagarjogadia28.core.usecase.FilterOutDigitsUseCase
import com.sagarjogadia28.core.util.UiEvent
import com.sagarjogadia28.core.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AgeViewModel(
    private val preferences: UserInfoPreferences,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase
) : ViewModel() {

    var age by mutableStateOf("20")
        private set

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    fun onAgeUpdated(age: String) {
        if (age.length <= 3)
            this.age = filterOutDigitsUseCase(age)
    }

    fun saveAge() {
        viewModelScope.launch {
            val ageNumber = age.toIntOrNull()
            if (ageNumber == null || ageNumber == 0) {
                _uiChannel.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_age_cant_be_empty)))
                return@launch
            }
            preferences.saveAge(age.toInt())
            _uiChannel.send(UiEvent.Navigate)
        }
    }
}