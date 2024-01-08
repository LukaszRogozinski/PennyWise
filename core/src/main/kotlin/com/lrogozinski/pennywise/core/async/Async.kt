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
package com.lrogozinski.pennywise.core.async
/**
 * Describes generic asynchronous [value].
 *
 * @param T The type of the [value].
 */
sealed class Async<out T> {

    /**
     * By default, [value] is set to `null`.
     */
    open val value: T? get() = null

    /**
     * Unwraps the [value] if present, throws an [IllegalArgumentException] otherwise.
     *
     * @param message optional custom error message.
     * @return not optional [value]
     */
    fun unwrap(message: String? = null): T = if (message != null) {
        requireNotNull(value) { message }
    } else {
        requireNotNull(value)
    }
}

/**
 * Represents uninitialized state.
 */
data object Uninitialized : Async<Nothing>()

/**
 * Represents loading state with optional progress rate.
 */
data class Loading(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    val progress: Float? = null
) : Async<Nothing>()

/**
 * Stores successfully loaded [value].
 */
data class Success<out T>(override val value: T) : Async<T>()

/**
 * Represents failure when loading the [value] and stores corresponding [error].
 */
data class Fail<out T>(val error: Throwable) : Async<T>()
