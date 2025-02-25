package com.sagarjogadia28.core.domain.model

enum class GoalType {
    LOSE, KEEP, GAIN;

    companion object {
        fun fromString(goalType: String): GoalType {
            return when (goalType.uppercase()) {
                "LOSE" -> LOSE
                "KEEP" -> KEEP
                "GAIN" -> GAIN
                else -> KEEP
            }
        }
    }
}