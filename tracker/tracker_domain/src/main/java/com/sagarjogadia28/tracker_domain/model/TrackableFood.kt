package com.sagarjogadia28.tracker_domain.model

data class TrackableFood(
    val imageFrontThumbUrl: String?,
    val productName: String?,
    val carbohydrates100g: Int,
    val fat100g: Int,
    val proteins100g: Int,
    val energyKcal100g: Int,
    val id: Int? = null
)
