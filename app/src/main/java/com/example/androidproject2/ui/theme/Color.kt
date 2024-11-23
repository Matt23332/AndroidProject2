package com.example.androidproject2.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Define base colors for a farming theme
val LeafGreen = Color(0xFF8ABF2C) // Vibrant green for crops/leaves
val FieldGreen = Color(0xFF2F6B1A) // Deeper green for fields
val SoilBrown = Color(0xFF8B4513) // Earthy brown for soil
val WheatYellow = Color(0xFFF5DEB3) // Wheat-like yellow
val SkyBlue = Color(0xFF87CEEB) // Light blue for the sky
val SunshineYellow = Color(0xFFFFD700) // Bright yellow for the sun
val NeutralWhite = Color(0xFFFFFFFF) // Neutral white
val NeutralDark = Color(0xFF3D3B3B) // Dark neutral for text or details

@Immutable
data class AppColors(
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val secondarySurface: Color,
    val onSecondarySurface: Color,
    val regularSurface: Color,
    val onRegularSurface: Color,
    val actionSurface: Color,
    val onActionSurface: Color,
    val highlightSurface: Color,
    val onHighlightSurface: Color,
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        secondarySurface = Color.Unspecified,
        onSecondarySurface = Color.Unspecified,
        regularSurface = Color.Unspecified,
        onRegularSurface = Color.Unspecified,
        actionSurface = Color.Unspecified,
        onActionSurface = Color.Unspecified,
        highlightSurface = Color.Unspecified,
        onHighlightSurface = Color.Unspecified
    )
}

// Extended colors for a farming theme
val extendedColors = AppColors(
    background = WheatYellow, // Warm yellow for backgrounds
    onBackground = NeutralDark, // Neutral dark for text on backgrounds
    surface = NeutralWhite, // Clean white for surfaces
    onSurface = NeutralDark, // Dark neutral for text on surfaces
    secondarySurface = LeafGreen, // Vibrant green for highlights or accents
    onSecondarySurface = NeutralWhite, // White for text on secondary surfaces
    regularSurface = SoilBrown, // Earthy brown for regular surfaces
    onRegularSurface = NeutralWhite, // White for text on regular surfaces
    actionSurface = SunshineYellow, // Bright yellow for actions like buttons
    onActionSurface = NeutralDark, // Dark neutral for text on action surfaces
    highlightSurface = FieldGreen, // Deep green for highlights
    onHighlightSurface = NeutralWhite // White for text on highlight surfaces
)
