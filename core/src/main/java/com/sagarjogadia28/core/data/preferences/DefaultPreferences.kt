package com.sagarjogadia28.core.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sagarjogadia28.core.domain.model.ActivityLevel
import com.sagarjogadia28.core.domain.model.Gender
import com.sagarjogadia28.core.domain.model.GoalType
import com.sagarjogadia28.core.domain.model.UserInfo
import com.sagarjogadia28.core.domain.preferences.UserInfoPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultPreferences(
    private val dataStore: DataStore<Preferences>
) : UserInfoPreferences {

    override suspend fun saveGender(gender: Gender) {
        dataStore.edit { preferences ->
            preferences[KEY_GENDER] = gender.name
        }
    }

    override suspend fun saveAge(age: Int) {
        dataStore.edit { preferences ->
            preferences[KEY_AGE] = age
        }
    }

    override suspend fun saveWeight(weight: Float) {
        dataStore.edit { preferences ->
            preferences[KEY_WEIGHT] = weight
        }
    }

    override suspend fun saveHeight(height: Int) {
        dataStore.edit { preferences ->
            preferences[KEY_HEIGHT] = height
        }
    }

    override suspend fun saveActivityLevel(level: ActivityLevel) {
        dataStore.edit { preferences ->
            preferences[KEY_ACTIVITY_LEVEL] = level.name
        }
    }

    override suspend fun saveGoalType(type: GoalType) {
        dataStore.edit { preferences ->
            preferences[KEY_GOAL_TYPE] = type.name
        }
    }

    override suspend fun saveCarbRatio(ratio: Float) {
        dataStore.edit { preferences ->
            preferences[KEY_CARB_RATIO] = ratio
        }
    }

    override suspend fun saveProteinRatio(ratio: Float) {
        dataStore.edit { preferences ->
            preferences[KEY_PROTEIN_RATIO] = ratio
        }
    }

    override suspend fun saveFatRatio(ratio: Float) {
        dataStore.edit { preferences ->
            preferences[KEY_FAT_RATIO] = ratio
        }
    }

    override fun loadUserInfo(): Flow<UserInfo> {
        return dataStore.data.map { preferences ->
            UserInfo(
                gender = Gender.fromString(preferences[KEY_GENDER] ?: "FEMALE"),
                age = preferences[KEY_AGE] ?: -1,
                weight = preferences[KEY_WEIGHT] ?: -1f,
                height = preferences[KEY_HEIGHT] ?: -1,
                activityLevel = ActivityLevel.fromString(
                    preferences[KEY_ACTIVITY_LEVEL] ?: "MEDIUM"
                ),
                goalType = GoalType.fromString(preferences[KEY_GOAL_TYPE] ?: "KEEP"),
                carbRatio = preferences[KEY_CARB_RATIO] ?: 0f,
                proteinRatio = preferences[KEY_PROTEIN_RATIO] ?: 0f,
                fatRatio = preferences[KEY_FAT_RATIO] ?: 0f
            )
        }
    }

    companion object {
        val KEY_GENDER = stringPreferencesKey("gender")
        val KEY_AGE = intPreferencesKey("age")
        val KEY_WEIGHT = floatPreferencesKey("weight")
        val KEY_HEIGHT = intPreferencesKey("height")
        val KEY_ACTIVITY_LEVEL = stringPreferencesKey("activity_level")
        val KEY_GOAL_TYPE = stringPreferencesKey("goal_type")
        val KEY_CARB_RATIO = floatPreferencesKey("carb_ratio")
        val KEY_PROTEIN_RATIO = floatPreferencesKey("protein_ratio")
        val KEY_FAT_RATIO = floatPreferencesKey("fat_ratio")
    }
}