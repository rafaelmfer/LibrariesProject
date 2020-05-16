package rafaelmfer.customviews.extensions

import android.content.res.Resources
import kotlin.math.roundToInt

fun Int.dpToPx(): Int =
    (this * Resources.getSystem().displayMetrics.density + 0.5f).roundToInt()

fun Int.pxToDp(): Int =
    (this / Resources.getSystem().displayMetrics.density + 0.5f).roundToInt()

fun Float.dpToPx(): Int =
    (this * Resources.getSystem().displayMetrics.density + 0.5f).roundToInt()

fun Float.pxToDp(): Int =
    (this / Resources.getSystem().displayMetrics.density + 0.5f).roundToInt()