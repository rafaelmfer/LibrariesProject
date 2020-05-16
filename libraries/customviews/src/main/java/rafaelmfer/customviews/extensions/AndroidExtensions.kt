package rafaelmfer.customviews.extensions

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.toggleTextInputLayoutError(errorEnabled: Boolean, errorText: String? = null) {
    errorText?.let {
        if (it.isNotEmpty()) {
            error = errorText
        }
    }
    isErrorEnabled = errorEnabled
}

/**
 * Funções para obter Resources de cores e drawables
 */

fun Context.getResAttrColor(@AttrRes idAttrColor: Int): Int = TypedValue().run {
    theme.resolveAttribute(idAttrColor, this, true)
    data
}

fun Context.getResColor(@ColorRes idColor: Int) = ContextCompat.getColor(this, idColor)
fun Fragment.getResColor(@ColorRes idColor: Int) = requireContext().getResColor(idColor)

fun Context.getResDrawable(@DrawableRes idDrawableRes: Int) = ContextCompat.getDrawable(this, idDrawableRes)
fun Fragment.getResDrawable(@DrawableRes idDrawableRes: Int) = requireContext().getResDrawable(idDrawableRes)

/**
 *  Função para esconder o teclado seja em Activity, Fragment, View, Dialog, etc.
 */

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

/**
 * Função para verificar se a acessibilidade está ligada e fazer announces do talkback
 */

val Context.accessibilityManager: AccessibilityManager
    get() = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

fun Context.isAccessibilityEnabled() = accessibilityManager.isEnabled
fun Fragment.isAccessibilityEnabled() = context?.isAccessibilityEnabled()

fun Context.isScreenReaderEnabled(): Boolean {
    if (!accessibilityManager.isEnabled) return false

    val serviceInfoList =
        accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_SPOKEN)
    if (serviceInfoList.isNullOrEmpty()) return false

    return true
}

fun Context.announceAccessibility(@StringRes stringId: Int) = announceAccessibility(getString(stringId))

fun Context.announceAccessibility(stringRes: String) {
    val event = AccessibilityEvent.obtain().apply {
        eventType = AccessibilityEvent.TYPE_ANNOUNCEMENT
        className = javaClass.name
        packageName = packageName
        text.add(stringRes)
    }
    if (isAccessibilityEnabled()) accessibilityManager.sendAccessibilityEvent(event)
}
