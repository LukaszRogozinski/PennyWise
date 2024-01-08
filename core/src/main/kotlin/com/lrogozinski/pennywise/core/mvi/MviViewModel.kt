package com.lrogozinski.pennywise.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lrogozinski.pennywise.core.asyncstate.AsyncState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<S, C>(
    initialState: S
) : ViewModel(), AsyncState<S> by AsyncState.Delegate(initialState) {
    override val stateFlow = MutableStateFlow(initialState)

    protected abstract fun onCommand(command: C)

    fun dispatch(command: C) = viewModelScope.launch { onCommand(command) }
}
