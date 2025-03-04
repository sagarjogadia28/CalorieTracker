package com.sagarjogadia28.tracker_data.repository

import com.sagarjogadia28.tracker_data.local.TrackerDao
import com.sagarjogadia28.tracker_data.mapper.toTrackableFood
import com.sagarjogadia28.tracker_data.mapper.toTrackedFood
import com.sagarjogadia28.tracker_data.mapper.toTrackedFoodEntity
import com.sagarjogadia28.tracker_data.remote.OpenFoodApi
import com.sagarjogadia28.tracker_domain.model.TrackableFood
import com.sagarjogadia28.tracker_domain.model.TrackedFood
import com.sagarjogadia28.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {
    override suspend fun upsertTrackedFood(trackedFood: TrackedFood) {
        dao.upsertTrackedFood(trackedFood.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(trackedFood: TrackedFood) {
        dao.deleteTrackedFood(trackedFood.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(date: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            day = date.dayOfMonth,
            month = date.monthValue,
            year = date.year
        ).map { entityList ->
            entityList.map { trackedFoodEntity ->
                trackedFoodEntity.toTrackedFood()
            }
        }
    }

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(query, page, pageSize)
            Result.success(searchDto.products.mapNotNull { product ->
                product.toTrackableFood()
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}