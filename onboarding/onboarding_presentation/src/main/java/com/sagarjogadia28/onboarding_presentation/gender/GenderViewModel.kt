package com.sagarjogadia28.onboarding_presentation.gender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagarjogadia28.core.domain.model.Gender
import com.sagarjogadia28.core.domain.preferences.UserInfoPreferences
import com.sagarjogadia28.core.navigation.Route
import com.sagarjogadia28.core.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class GenderViewModel(
    private val preferences: UserInfoPreferences
) : ViewModel() {

    var gender by mutableStateOf(Gender.FEMALE)
        private set

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    fun onGenderSelected(gender: Gender) {
        this.gender = gender
    }

    fun saveGender() {
        viewModelScope.launch {
            preferences.saveGender(gender)
            _uiChannel.send(UiEvent.Navigate(Route.Age))
        }
    }
}