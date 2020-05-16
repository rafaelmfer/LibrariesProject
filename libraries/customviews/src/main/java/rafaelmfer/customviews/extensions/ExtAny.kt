package rafaelmfer.customviews.extensions

import java.math.BigDecimal.ZERO

val <T> T.hasValue
    get() = when (this) {
        null -> false
        false -> false
        ZERO -> false
        EMPTY_STRING -> false
        ZERO.toString() -> false
        ZERO.toLong() -> false
        ZERO.toDouble() -> false
        ZERO.toFloat() -> false
        arrayListOf<T>() -> false
        listOf<T>() -> false
        {} -> false
        else -> true
    }

fun <T, R> T.ifValid(function: () -> R?) = if (hasValue) function.invoke() else null

fun <T> forEachArg(vararg any: T, function: T.() -> Unit) = any.forEach { function.invoke(it) }

infix fun <T> T.otherWise(other: T) = if (hasValue) this else other

infix fun <T> T.safely(function: T.() -> Unit) { if (hasValue) function.invoke(this) }
