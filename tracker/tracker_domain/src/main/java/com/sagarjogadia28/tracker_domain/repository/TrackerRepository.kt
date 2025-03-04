package com.sagarjogadia28.tracker_domain.repository

import com.sagarjogadia28.tracker_domain.model.TrackableFood
import com.sagarjogadia28.tracker_domain.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TrackerRepository {
    suspend fun upsertTrackedFood(trackedFood: TrackedFood)
    suspend fun deleteTrackedFood(trackedFood: TrackedFood)
    fun getFoodsForDate(date: LocalDate): Flow<List<TrackedFood>>
    suspend fun searchFood(query: String, page: Int, pageSize: Int): Result<List<TrackableFood>>
}