package com.sagarjogadia28.onboarding_presentation.mock

import com.sagarjogadia28.core.domain.model.ActivityLevel
import com.sagarjogadia28.core.domain.model.Gender
import com.sagarjogadia28.core.domain.model.GoalType
import com.sagarjogadia28.core.domain.model.UserInfo
import com.sagarjogadia28.core.domain.preferences.UserInfoPreferences
import kotlinx.coroutines.flow.Flow

class FakePreferences : UserInfoPreferences {
    override suspend fun saveGender(gender: Gender) {
        TODO("Not yet implemented")
    }

    override suspend fun saveAge(age: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun saveWeight(weight: Float) {
        TODO("Not yet implemented")
    }

    override suspend fun saveHeight(height: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun saveActivityLevel(level: ActivityLevel) {
        TODO("Not yet implemented")
    }

    override suspend fun saveGoalType(type: GoalType) {
        TODO("Not yet implemented")
    }

    override suspend fun saveCarbRatio(ratio: Float) {
        TODO("Not yet implemented")
    }

    override suspend fun saveProteinRatio(ratio: Float) {
        TODO("Not yet implemented")
    }

    override suspend fun saveFatRatio(ratio: Float) {
        TODO("Not yet implemented")
    }

    override fun loadUserInfo(): Flow<UserInfo> {
        TODO("Not yet implemented")
    }
}
