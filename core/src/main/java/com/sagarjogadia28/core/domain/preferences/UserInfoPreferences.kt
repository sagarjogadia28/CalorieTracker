package com.sagarjogadia28.core.domain.preferences

import com.sagarjogadia28.core.domain.model.ActivityLevel
import com.sagarjogadia28.core.domain.model.Gender
import com.sagarjogadia28.core.domain.model.GoalType
import com.sagarjogadia28.core.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserInfoPreferences {
    suspend fun saveGender(gender: Gender)
    suspend fun saveAge(age: Int)
    suspend fun saveWeight(weight: Float)
    suspend fun saveHeight(height: Int)
    suspend fun saveActivityLevel(level: ActivityLevel)
    suspend fun saveGoalType(type: GoalType)
    suspend fun saveCarbRatio(ratio: Float)
    suspend fun saveProteinRatio(ratio: Float)
    suspend fun saveFatRatio(ratio: Float)
    suspend fun saveShouldShowOnboarding(showOnboarding: Boolean)

    fun loadUserInfo(): Flow<UserInfo>
    fun loadShouldShowOnboarding(): Flow<Boolean>
}