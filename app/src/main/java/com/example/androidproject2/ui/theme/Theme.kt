package com.example.androidproject2.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Define farming theme color schemes
private val DarkColorScheme = darkColorScheme(
    primary = FieldGreen,
    onPrimary = NeutralWhite,
    secondary = LeafGreen,
    onSecondary = NeutralWhite,
    background = SoilBrown,
    onBackground = NeutralWhite,
    surface = FieldGreen,
    onSurface = NeutralWhite
)

private val LightColorScheme = lightColorScheme(
    primary = LeafGreen,
    onPrimary = NeutralWhite,
    secondary = SunshineYellow,
    onSecondary = NeutralDark,
    background = WheatYellow,
    onBackground = NeutralDark,
    surface = NeutralWhite,
    onSurface = NeutralDark
)

@Composable
fun AndroidProject2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppColors provides extendedColors,
        LocalAppTypography provides extendedTypography
    ) {
        MaterialTheme(
            content = content
        )
    }
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current
}
