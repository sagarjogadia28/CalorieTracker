package com.sagarjogadia28.core.util

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class StringText(val text: String) : UiText()
    data class StringResource(@StringRes val id: Int) : UiText()

    fun asString(context: Context): String {
        return when(this) {
            is StringResource ->  context.getString(id)
            is StringText -> text
        }
    }
}