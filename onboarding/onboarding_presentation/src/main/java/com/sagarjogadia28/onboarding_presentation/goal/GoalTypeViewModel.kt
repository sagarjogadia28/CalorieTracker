package com.sagarjogadia28.onboarding_presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagarjogadia28.core.domain.model.GoalType
import com.sagarjogadia28.core.domain.preferences.UserInfoPreferences
import com.sagarjogadia28.core.navigation.Route
import com.sagarjogadia28.core.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class GoalTypeViewModel(
    private val preferences: UserInfoPreferences
) : ViewModel() {

    var goalType by mutableStateOf(GoalType.KEEP)
        private set

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    fun onGoalTypeSelected(goalType: GoalType) {
        this.goalType = goalType
    }

    fun saveGoalType() {
        viewModelScope.launch {
            preferences.saveGoalType(goalType)
            _uiChannel.send(UiEvent.Navigate)
        }
    }
}