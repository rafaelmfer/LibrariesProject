@file:Suppress("UNCHECKED_CAST")

package rafaelmfer.customviews.extensions

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import rafaelmfer.customviews.baseviews.ActivityContract

fun Activity.setStatusBarColor(@ColorRes colorId: Int, hasLightTextColor: Boolean = true) {
    window.statusBarColor = getResColor(colorId)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (hasLightTextColor) window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    container: Int,
    stackAdd: Boolean = false
) {
    supportFragmentManager.ensureTransaction().run {
        if (stackAdd) addToBackStack(fragment.javaClass.name)
        supportFragmentManager.setFragmentsVisibleHint(fragment, container)
        replace(container, fragment, fragment.javaClass.name).commit()
    }
}

fun ActivityContract.replaceFragment(
    fragment: Fragment,
    stackAdd: Boolean = true
) {
    activityContract.run {
        supportFragmentManager.ensureTransaction().run {
            if (stackAdd) addToBackStack(fragment.javaClass.name)
            supportFragmentManager.setFragmentsVisibleHint(fragment, container)
            replace(container, fragment, fragment.javaClass.name).commit()
        }
    }
}
