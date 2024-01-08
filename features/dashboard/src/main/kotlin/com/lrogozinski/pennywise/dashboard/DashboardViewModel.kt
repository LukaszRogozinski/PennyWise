package com.lrogozinski.pennywise.dashboard

import com.lrogozinski.pennywise.core.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() :
    MviViewModel<DashboardViewState, DashboardCommand>(DashboardViewState()) {

        override fun onCommand(command: DashboardCommand) {}
}
