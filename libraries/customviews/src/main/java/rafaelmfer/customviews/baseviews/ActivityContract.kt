package rafaelmfer.customviews.baseviews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

interface ActivityContract {

    val activityContract get() = this as AppCompatActivity

    val container: Int

    fun bundleRouter(fragID: Int, bundle: Bundle? = null) {}

    fun onBackPress() = activityContract.onBackPressed()

    fun popBackStack() = activityContract.supportFragmentManager.popBackStack()
}