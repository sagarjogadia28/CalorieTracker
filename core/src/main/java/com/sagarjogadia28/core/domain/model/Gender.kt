package com.sagarjogadia28.core.domain.model

enum class Gender {
    MALE, FEMALE;

    companion object {
        fun fromString(gender: String): Gender {
            return when (gender.uppercase()) {
                "MALE" -> MALE
                "FEMALE" -> FEMALE
                else -> FEMALE
            }
        }
    }
}