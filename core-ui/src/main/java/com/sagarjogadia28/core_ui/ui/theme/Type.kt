package com.sagarjogadia28.core_ui.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    displayLarge = TextStyle( // Equivalent to h1
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ),
    displayMedium = TextStyle( // Equivalent to h2
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp
    ),
    displaySmall = TextStyle( // Equivalent to h3
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    headlineLarge = TextStyle( // Equivalent to h4
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    headlineMedium = TextStyle( // Equivalent to h5
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle( // Equivalent to body1
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle( // Equivalent to button
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp
    ),
    bodySmall = TextStyle( // Equivalent to caption
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)