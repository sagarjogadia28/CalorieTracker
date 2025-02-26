package com.sagarjogadia28.core.usecase

class FilterOutDigitsUseCase {
    operator fun invoke(text: String): String {
        return text.filter { it.isDigit() }
    }
}