package com.sagarjogadia28.tracker_data.mapper

import com.sagarjogadia28.tracker_data.remote.dto.Product
import com.sagarjogadia28.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun Product.toTrackableFood(): TrackableFood? {
    return TrackableFood(
        productName = productName ?: return null,
        imageFrontThumbUrl = imageFrontThumbUrl,
        carbohydrates100g = nutriments.carbohydrates100g.roundToInt(),
        fat100g = nutriments.fat100g.roundToInt(),
        proteins100g = nutriments.proteins100g.roundToInt(),
        energyKcal100g = nutriments.energyKcal100g.roundToInt(),
    )
}