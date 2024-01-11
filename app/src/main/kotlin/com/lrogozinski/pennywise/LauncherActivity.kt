package com.lrogozinski.pennywise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lrogozinski.pennywise.core.ui.locals.LocalActivity
import com.lrogozinski.pennywise.core.ui.locals.LocalAppNavigator
import com.lrogozinski.pennywise.core.ui.theme.AppTheme
import com.lrogozinski.pennywise.dashboard.navigation.dashboardNavGraph
import com.lrogozinski.pennywise.navigation.AppGraph
import com.lrogozinski.pennywise.navigation.AppNavigatorImpl
import com.lrogozinski.pennywise.navigation.NavigationArgs
import com.lrogozinski.pennywise.navigation.routes.AppRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LauncherActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val navHostController = rememberNavController()
            CompositionLocalProvider(
                LocalActivity provides this,
                LocalAppNavigator provides AppNavigatorImpl(navHostController)
            ) {
                AppTheme {
                    AppGraph(navHostController = navHostController)
                }
            }
        }
    }
}
