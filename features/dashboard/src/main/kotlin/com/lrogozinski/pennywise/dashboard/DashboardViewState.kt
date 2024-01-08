package com.lrogozinski.pennywise.dashboard

import com.lrogozinski.pennywise.core.async.Async
import com.lrogozinski.pennywise.core.async.Uninitialized

data class DashboardViewState(
    val test: Async<String> = Uninitialized
)
