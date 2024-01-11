package com.lrogozinski.pennywise.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.lrogozinski.pennywise.dashboard.navigation.dashboardNavGraph
import com.lrogozinski.pennywise.navigation.routes.AppRoutes

@Composable
internal fun AppGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = AppRoutes.Dashboard
    ) {
        dashboardNavGraph()
    }
}
