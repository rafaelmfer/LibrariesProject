package rafaelmfer.customviews.baseviews

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import rafaelmfer.customviews.R

open class ActBase(open val layout: Int = R.layout.act_frame) : AppCompatActivity(), IPermissionResult {

    open val view: Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exceptionHandler?.let {
            Thread.setDefaultUncaughtExceptionHandler(it.newInstance())
        }
        intent?.extras?.onExtras()
        if (layout != 0) {
            setContentView(layout)
            ((window.decorView.rootView as ViewGroup).getChildAt(0) as ViewGroup).onView()
        } else if (view is View) {
            setContentView(view as View)
            (view as ViewGroup).onView()
        }
    }

    override fun onResume() {
        super.onResume()
        currentActivity = this
    }

    open fun Bundle.onExtras() {}

    open fun ViewGroup.onView() {}

    override var iPermissionRequest: IPermissionRequest? = null

    override fun onRequestPermissionsResult(code: Int, permissions: Array<out String>, results: IntArray) =
        requestPermissionsResult(code, permissions, results)

    companion object {
        @JvmStatic
        lateinit var currentActivity: AppCompatActivity

        @JvmStatic
        var exceptionHandler: Class<out Thread.UncaughtExceptionHandler>? = null
    }
}