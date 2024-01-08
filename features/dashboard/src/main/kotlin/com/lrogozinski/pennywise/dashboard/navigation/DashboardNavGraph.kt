package com.lrogozinski.pennywise.dashboard.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.lrogozinski.pennywise.dashboard.Dashboard
import com.lrogozinski.pennywise.dashboard.DashboardViewModel
import com.lrogozinski.pennywise.navigation.graph.mvi
import com.lrogozinski.pennywise.navigation.routes.AppRoutes


fun NavGraphBuilder.dashboardNavGraph() {
    mvi(
        route = AppRoutes.Dashboard,
        viewModelFactory = { hiltViewModel<DashboardViewModel>() }
    ) { state, commands ->
        Dashboard(state, commands)
    }
}
