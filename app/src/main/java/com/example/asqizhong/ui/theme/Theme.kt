package com.example.asqizhong.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Teal40,
    onPrimary = Color.White,
    primaryContainer = TealContainer40,
    onPrimaryContainer = Color(0xFF002021),
    secondary = Amber40,
    onSecondary = Color.White,
    secondaryContainer = AmberContainer40,
    onSecondaryContainer = Color(0xFF331200),
    tertiary = Rose40,
    background = PaperLight,
    onBackground = InkLight,
    surface = SurfaceLight,
    onSurface = InkLight,
    surfaceVariant = Color(0xFFDAE4E3),
    onSurfaceVariant = Color(0xFF3F4948)
)

private val DarkColorScheme = darkColorScheme(
    primary = Teal80,
    onPrimary = Color(0xFF003739),
    primaryContainer = TealContainer80,
    onPrimaryContainer = TealContainer40,
    secondary = Amber80,
    onSecondary = Color(0xFF5B3A00),
    secondaryContainer = AmberContainer80,
    onSecondaryContainer = AmberContainer40,
    tertiary = Rose80,
    background = PaperDark,
    onBackground = InkDark,
    surface = SurfaceDark,
    onSurface = InkDark,
    surfaceVariant = Color(0xFF3F4948),
    onSurfaceVariant = Color(0xFFBEC9C8)
)

@Composable
fun AsqizhongTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
