package com.lrogozinski.pennywise.core.ui.locals

import android.app.Activity
import androidx.compose.runtime.staticCompositionLocalOf

val LocalActivity = staticCompositionLocalOf<Activity> {
    error("No Activity provided.")
}
