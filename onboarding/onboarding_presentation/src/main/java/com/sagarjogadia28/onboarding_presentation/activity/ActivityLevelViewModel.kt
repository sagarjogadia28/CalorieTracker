package com.sagarjogadia28.onboarding_presentation.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagarjogadia28.core.domain.model.ActivityLevel
import com.sagarjogadia28.core.domain.preferences.UserInfoPreferences
import com.sagarjogadia28.core.navigation.Route
import com.sagarjogadia28.core.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ActivityLevelViewModel(
    private val preferences: UserInfoPreferences
) : ViewModel() {

    var activityLevel by mutableStateOf(ActivityLevel.MEDIUM)
        private set

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    fun onActivityLevelSelected(activityLevel: ActivityLevel) {
        this.activityLevel = activityLevel
    }

    fun saveActivityLevel() {
        viewModelScope.launch {
            preferences.saveActivityLevel(activityLevel)
            _uiChannel.send(UiEvent.Navigate(Route.Goal))
        }
    }
}