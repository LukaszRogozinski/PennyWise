package com.lrogozinski.pennywise.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.lrogozinski.pennywise.resources.theme.ColorSchemes
import com.lrogozinski.pennywise.resources.theme.ThemeElements

@Composable
fun ComponentTheme(
    darkTheme: ColorScheme = ColorSchemes.Dark,
    lightTheme: ColorScheme = ColorSchemes.Light,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides if (isDarkTheme) {
            darkTheme.onBackground
        } else {
            lightTheme.onBackground
        }
    ) {
        val colorScheme =
            when {
                dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    val context = LocalContext.current
                    if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
                        context
                    )
                }
                isDarkTheme -> darkTheme
                else -> lightTheme
            }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = ThemeElements.Typography,
            shapes = ThemeElements.Shapes,
            content = content
        )
    }
}
