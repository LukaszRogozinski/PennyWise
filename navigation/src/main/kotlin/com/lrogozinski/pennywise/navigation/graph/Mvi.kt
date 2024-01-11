package com.lrogozinski.pennywise.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lrogozinski.pennywise.core.mvi.MviViewModel
import com.lrogozinski.pennywise.navigation.NavigationArgs

/**
 * Add the [Composable] to the [NavGraphBuilder]
 *
 * @param route route for the destination
 * @param deepLinks list of deep links to associate with the destinations
 * @param viewModelFactory used to create ViewModel
 * @param content composable for the destination
 */
fun <S, C> NavGraphBuilder.mvi(
    route: String,
    deepLinks: List<NavDeepLink> = emptyList(),
    viewModelFactory: @Composable () -> MviViewModel<S, C>,
    content: @Composable (state: S, commands: (C) -> Unit) -> Unit,
) = composable(
    route = route,
    deepLinks = deepLinks,
    arguments = listOf(
        navArgument(NavigationArgs.Key) { nullable = true }
    )
) {
    val viewModel = viewModelFactory()

    content(
        viewModel
            .stateFlow
            .collectAsState()
            .value,
        viewModel::dispatch,
    )
}
