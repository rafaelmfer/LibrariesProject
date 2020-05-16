package rafaelmfer.customviews.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import kotlin.reflect.KClass

val Context.scanForAct : Activity? get() = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.scanForAct
    else -> null
}

fun Context.startActivity(kClass: KClass<*>, extras: Bundle? = null) = Intent(this, kClass.java).let {
    if (extras is Bundle) it.putExtras(extras)
    startActivity(it)
}

fun View.startActivity(kClass: KClass<*>, extras: Bundle? = null) = Intent(context, kClass.java).let {
    if (extras is Bundle) it.putExtras(extras)
    context.startActivity(it)
}

infix fun Context.stringAny(text: Any?): CharSequence = when (text) {
    is String -> text
    is CharSequence -> text
    is Int -> getResourceOrToString(text)
    else -> EMPTY_STRING
}

private fun Context.getResourceOrToString(text: Int) = try {
    getString(text)
} catch (ex: Resources.NotFoundException) {
    text.toString()
}