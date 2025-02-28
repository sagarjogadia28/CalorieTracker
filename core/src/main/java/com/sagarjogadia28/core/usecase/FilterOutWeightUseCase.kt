package com.sagarjogadia28.core.usecase

class FilterOutWeightUseCase {
    operator fun invoke(weight: String): String {
        val filtered = weight.filterIndexed { index, c ->
            (c.isDigit() || c == '.' && weight.indexOf('.') == index)
        }

        val parts = filtered.split('.')
        return if (parts.size > 1) {
            parts[0] + "." + parts[1].take(2)
        } else
            filtered
    }
}