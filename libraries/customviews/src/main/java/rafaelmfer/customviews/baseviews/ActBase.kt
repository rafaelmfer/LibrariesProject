package rafaelmfer.customviews.baseviews

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import rafaelmfer.customviews.R

open class ActBase(open val layout: Any? = R.layout.act_frame) : AppCompatActivity(), IPermissionResult {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exceptionHandler?.let {
            Thread.setDefaultUncaughtExceptionHandler(it.newInstance())
        }
        when (layout) {
            is Int -> setContentView(layout as Int)
            is View -> setContentView(layout as View)
        }
        intent?.extras?.onExtras()
        onView()
    }

    override fun onResume() {
        super.onResume()
        currentActivity = this
    }

    open fun Bundle.onExtras() {}

    open fun onView() {}

    override var iPermissionRequest: IPermissionRequest? = null

    override fun onRequestPermissionsResult(
        code: Int,
        permissions: Array<out String>,
        results: IntArray
    ) = requestPermissionsResult(code, permissions, results)

    companion object {
        @JvmStatic
        lateinit var currentActivity: AppCompatActivity

        @JvmStatic
        var exceptionHandler: Class<out Thread.UncaughtExceptionHandler>? = null
    }
}