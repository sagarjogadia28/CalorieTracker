package com.sagarjogadia28.tracker_data.mapper

import com.sagarjogadia28.tracker_data.remote.dto.Product
import com.sagarjogadia28.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun Product.toTrackableFood(): TrackableFood? {
    return TrackableFood(
        productName = productName ?: return null,
        imageFrontThumbUrl = imageFrontThumbUrl,
        carbohydrates100g = nutriments?.carbohydrates100g?.roundToInt() ?: 0,
        fat100g = nutriments?.fat100g?.roundToInt() ?: 0,
        proteins100g = nutriments?.proteins100g?.roundToInt() ?: 0,
        energyKcal100g = nutriments?.energyKcal100g?.roundToInt() ?: 0,
    )
}