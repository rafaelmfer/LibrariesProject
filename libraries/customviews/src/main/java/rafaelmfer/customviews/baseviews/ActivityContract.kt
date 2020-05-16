package rafaelmfer.customviews.baseviews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

const val PERMISSION_REQUEST_CODE = 11

interface ActivityContract {

    val activity get() = this as AppCompatActivity

    val container: Int

    fun bundleRouter(fragID: Int, bundle: Bundle? = null) {}

    fun onBackPress() = activity.onBackPressed()

    fun popBackStack() = activity.supportFragmentManager.popBackStack()
}