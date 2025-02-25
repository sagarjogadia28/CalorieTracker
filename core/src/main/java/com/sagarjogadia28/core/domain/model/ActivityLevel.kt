package com.sagarjogadia28.core.domain.model

enum class ActivityLevel {
    LOW, MEDIUM, HIGH;

    companion object {
        fun fromString(activityLevel: String): ActivityLevel {
            return when (activityLevel.uppercase()) {
                "LOW" -> LOW
                "MEDIUM" -> MEDIUM
                "HIGH" -> HIGH
                else -> MEDIUM
            }
        }
    }
}