package com.sagarjogadia28.tracker_domain.usecase

import com.sagarjogadia28.tracker_domain.model.TrackableFood
import com.sagarjogadia28.tracker_domain.repository.TrackerRepository

class SearchFoodUseCase(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 40
    ): Result<List<TrackableFood>> {
        if (query.isBlank())
            return Result.success(emptyList())
        return repository.searchFood(query.trim(), page, pageSize)
    }
}
