package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CyberColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Color(0xFF002026),
    primaryContainer = Color(0xFF003844),
    onPrimaryContainer = Color(0xFF9CF4FF),
    secondary = NeonMagenta,
    onSecondary = Color(0xFF3B001B),
    secondaryContainer = Color(0xFF5A002B),
    onSecondaryContainer = Color(0xFFFFD9E4),
    tertiary = NeonAmber,
    onTertiary = Color(0xFF332000),
    tertiaryContainer = Color(0xFF553500),
    onTertiaryContainer = Color(0xFFFFE0A3),
    background = CyberBackground,
    onBackground = TextPrimary,
    surface = CyberSurface,
    onSurface = TextPrimary,
    surfaceVariant = CyberSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = CyberBorder,
    error = DarkError,
    onError = Color.White
)

@Composable
fun CineSonicTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CyberColorScheme,
        typography = Typography,
        content = content
    )
}
