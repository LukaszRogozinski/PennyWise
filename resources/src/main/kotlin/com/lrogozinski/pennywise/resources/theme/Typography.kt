package com.lrogozinski.pennywise.resources.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

internal val ThemeTypography = Typography(
    headlineLarge = TextStyle( //h4
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp
    ),
    headlineMedium = TextStyle( //h5
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle( //h6
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 1.5.em
    ),
    titleMedium = TextStyle( //subtitle1
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle( //subtitle2
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle( //body1
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 1.5.em
    ),
    bodyMedium = TextStyle( //body2
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 1.5.em
    ),
    labelLarge = TextStyle( //button
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle( //caption
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)
