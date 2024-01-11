package com.lrogozinski.pennywise.resources.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

object ColorSchemes {
    val Light = lightColorScheme(
        primary = BrandColors.Purple40,
        secondary = BrandColors.PurpleGrey40,
        tertiary = BrandColors.Pink40
    )

    val Dark = darkColorScheme(
        primary = BrandColors.Purple80,
        secondary = BrandColors.PurpleGrey80,
        tertiary = BrandColors.Pink80
    )
}
