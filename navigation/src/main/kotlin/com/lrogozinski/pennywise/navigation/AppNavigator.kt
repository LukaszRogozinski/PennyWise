package com.lrogozinski.pennywise.navigation

import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import kotlinx.coroutines.flow.StateFlow

interface AppNavigator {

    fun popBackStack(): Boolean

    fun navigate(
        route: String,
        args: String? = null,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    )

    fun <T> setDestinationResult(value: T?)

    fun <T> currentDestinationResult(initialValue: T): StateFlow<T>
}
