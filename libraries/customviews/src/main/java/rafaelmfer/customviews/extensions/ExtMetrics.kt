@file:Suppress("UNCHECKED_CAST")

package rafaelmfer.customviews.extensions

import android.content.res.Resources
import kotlin.math.roundToInt


val <Type : Number> Type.dp
    get() = (toFloat() * Resources.getSystem().displayMetrics.density) as Type

val <Type : Number> Type.dpToPx
    get() = (toFloat() / Resources.getSystem().displayMetrics.density) as Type

fun Number.asDP(toDP: Boolean = true) = (if (toDP)
    (this.toFloat() / Resources.getSystem().displayMetrics.density) else
    (this.toFloat() * Resources.getSystem().displayMetrics.density)
        ).roundToInt()

fun Number.asPX() = asDP(false)