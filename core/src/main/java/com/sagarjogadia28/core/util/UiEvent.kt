package com.sagarjogadia28.core.util

import com.sagarjogadia28.core.navigation.Route

sealed class UiEvent {
    data class Navigate(val route: Route) : UiEvent()
    data object NavigateUp : UiEvent()
    data class ShowSnackBar(val message: UiText): UiEvent()
}