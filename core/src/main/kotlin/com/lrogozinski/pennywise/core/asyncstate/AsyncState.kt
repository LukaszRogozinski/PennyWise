/**
 * MIT License
 *
 * Copyright (c) 2021 Denis T.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.lrogozinski.pennywise.core.asyncstate

import com.lrogozinski.pennywise.core.async.Async
import com.lrogozinski.pennywise.core.async.Fail
import com.lrogozinski.pennywise.core.async.Loading
import com.lrogozinski.pennywise.core.async.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty1

/**
 * Set of operators for UDF state management based on [Async].
 */
interface AsyncState<S> {

    /**
     * Delegate implementation of [AsyncState] based on [MutableStateFlow].
     */
    class Delegate<S>(initialState: S) : AsyncState<S> {
        override val stateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    }

    /**
     * [MutableStateFlow] with current state.
     */
    val stateFlow: MutableStateFlow<S>

    /**
     * Provide current state in [block]
     */
    fun withState(block: (state: S) -> Unit): Unit = block(stateFlow.value)

    /**
     * Update current state with [reducer].
     */
    fun setState(reducer: S.() -> S) {
        stateFlow.value = reducer(stateFlow.value)
    }

    /**
     * Subscribe for [property] changes in state.
     */
    fun <P> CoroutineScope.selectSubscribe(
        property: KProperty1<S, P>,
        block: (P) -> Unit
    ): Job = launch {
        stateFlow
            .map(property::get)
            .distinctUntilChanged()
            .collect(block)
    }

    /**
     * Collect flow of values wrapped in `Async` and update state with [reducer].
     */
    fun <V> Flow<Async<V>>.collectAsyncAsState(
        scope: CoroutineScope,
        initialState: Async<V>? = Loading(),
        reducer: S.(Async<V>) -> S
    ): Job = scope.launch {
        if (initialState != null) {
            setState { reducer(initialState) }
        }
        catch { setState { reducer(Fail(it)) } }
            .collect { setState { reducer(it) } }
    }

    /**
     * Collect flow of values wrapped in `Result` and update state with [reducer].
     */
    fun <V> Flow<Result<V>>.collectReduceAsState(
        scope: CoroutineScope,
        initialState: Async<V>? = Loading(),
        reducer: S.(Async<V>) -> S
    ): Job = scope.launch {
        if (initialState != null) {
            setState { reducer(initialState) }
        }
        catch { setState { reducer(Fail(it)) } }
            .collect {
                it.fold(
                    onFailure = { setState { reducer(Fail(it)) } },
                    onSuccess = { setState { reducer(Success(it)) } }
                )
            }
    }

    /**
     * Collect flow of values and update state with [reducer].
     */
    fun <V> Flow<V>.collectAsState(
        scope: CoroutineScope,
        initialState: Async<V>? = Loading(),
        reducer: S.(Async<V>) -> S
    ): Job = scope.launch {
        if (initialState != null) {
            setState { reducer(initialState) }
        }
        catch { setState { reducer(Fail(it)) } }
            .collect { setState { reducer(Success(it)) } }
    }

    /**
     * Await [result][Result] and update state with [reducer].
     */
     fun <V> ScopedDeferred<Result<V>>.reduceAsState(
        initialState: Async<V>? = Loading(),
        reducer: S.(Async<V>) -> S
    ): Job = let { (scope, value) ->
        scope.launch {
            if (initialState != null) {
                setState { reducer(initialState) }
            }
            value.await().fold(
                onFailure = { setState { reducer(Fail(it)) } },
                onSuccess = { setState { reducer(Success(it)) } }
            )
        }
    }

    /**
     * Await value and update state with [reducer].
     */
    fun <V> ScopedDeferred<V>.catchAsState(
        initialState: Async<V>? = Loading(),
        reducer: S.(Async<V>) -> S
    ): Job = let { (scope, value) ->
        scope.launch {
            if (initialState != null) {
                setState { reducer(initialState) }
            }
            try {
                val result = value.await()
                setState { reducer(Success(result)) }
            } catch (error: Throwable) {
                setState { reducer(Fail(error)) }
            }
        }
    }
}
