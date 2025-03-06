package com.sagarjogadia28.tracker_domain.usecase

import com.sagarjogadia28.tracker_domain.model.TrackedFood
import com.sagarjogadia28.tracker_domain.repository.TrackerRepository

class DeleteTrackedFoodUseCase(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(trackedFood: TrackedFood) {
        repository.deleteTrackedFood(trackedFood)
    }
}