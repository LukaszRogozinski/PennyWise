package com.lrogozinski.pennywise.navigation.routes

import com.lrogozinski.pennywise.navigation.NavigationArgs

internal fun routeTo(
    route: String,
    argument: String? = null
) = if (argument != null) {
    route.replace("{${NavigationArgs.Key}}", argument)
} else {
    route
}
