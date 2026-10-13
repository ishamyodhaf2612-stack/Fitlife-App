package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = FitGreenPrimary,
    onPrimary = FitGreenOnPrimary,
    primaryContainer = FitGreenPrimaryContainer,
    onPrimaryContainer = FitGreenOnPrimaryContainer,
    secondary = FitAquaSecondary,
    onSecondary = FitAquaOnSecondary,
    secondaryContainer = FitAquaSecondaryContainer,
    onSecondaryContainer = FitAquaOnSecondaryContainer,
    tertiary = FitOrangeTertiary,
    onTertiary = FitOrangeOnTertiary,
    tertiaryContainer = FitOrangeTertiaryContainer,
    onTertiaryContainer = FitOrangeOnTertiaryContainer,
    background = FitBackgroundDark,
    onBackground = FitOnBackgroundDark,
    surface = FitSurfaceDark,
    onSurface = FitOnSurfaceDark,
    surfaceVariant = FitSurfaceVariantDark,
    onSurfaceVariant = FitOnSurfaceVariantDark,
    outline = FitOutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = FitGreenPrimaryLight,
    onPrimary = FitGreenOnPrimaryLight,
    primaryContainer = FitGreenPrimaryContainerLight,
    onPrimaryContainer = FitGreenOnPrimaryContainerLight,
    secondary = FitAquaSecondary,
    onSecondary = FitAquaOnSecondary,
    secondaryContainer = FitAquaSecondaryContainer,
    onSecondaryContainer = FitAquaOnSecondaryContainer,
    tertiary = FitOrangeTertiary,
    onTertiary = FitOrangeOnTertiary,
    tertiaryContainer = FitOrangeTertiaryContainer,
    onTertiaryContainer = FitOrangeOnTertiaryContainer,
    background = FitBackgroundLight,
    onBackground = FitOnBackgroundLight,
    surface = FitSurfaceLight,
    onSurface = FitOnSurfaceLight,
    surfaceVariant = FitSurfaceVariantLight,
    onSurfaceVariant = FitOnSurfaceVariantLight,
    outline = FitOutlineLight
)

@Composable
fun FitLifeTheme(
    darkTheme: Boolean = true, // Default to sleek dark fitness theme
    dynamicColor: Boolean = false, // Keep consistent fitness branding
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).apply {
                    isAppearanceLightStatusBars = !darkTheme
                    isAppearanceLightNavigationBars = !darkTheme
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
