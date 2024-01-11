package com.lrogozinski.pennywise.core.ui.locals

import androidx.compose.runtime.compositionLocalOf
import com.lrogozinski.pennywise.navigation.AppNavigator

val LocalAppNavigator = compositionLocalOf<AppNavigator> {
    error("No AppNavigator provided.")
}
