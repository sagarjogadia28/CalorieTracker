package com.sagarjogadia28.tracker_presentation.search

import com.sagarjogadia28.tracker_domain.model.TrackableFood

data class TrackableFoodUiState(
    val trackableFood: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String = ""
)