package com.lrogozinski.pennywise.navigation

import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppNavigatorNoOp : AppNavigator {
    override fun popBackStack() = false
    override fun navigate(
        route: String,
        args: String?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
    }

    override fun <T> setDestinationResult(value: T?) {}

    override fun <T> currentDestinationResult(initialValue: T): StateFlow<T> =
        MutableStateFlow(initialValue)
}
