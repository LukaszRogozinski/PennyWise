package com.lrogozinski.pennywise.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.lrogozinski.pennywise.navigation.routes.routeTo
import kotlinx.coroutines.flow.StateFlow

class AppNavigatorImpl(private val navController: NavController): AppNavigator {
    override fun popBackStack(): Boolean = navController.popBackStack()

    override fun navigate(
        route: String,
        args: String?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?,
    ) {
        navController.navigate(
            route = routeTo(route, args),
            navOptions = navOptions,
            navigatorExtras = navigatorExtras
        )
    }

    override fun <T> setDestinationResult(value: T?) {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(NavigationArgs.Result, value)
    }

    override fun <T> currentDestinationResult(initialValue: T): StateFlow<T> =
        navController.currentBackStackEntry!!
            .savedStateHandle
            .getStateFlow(NavigationArgs.Result, initialValue)

}
