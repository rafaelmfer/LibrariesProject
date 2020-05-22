package rafaelmfer.customviews.baseviews

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import rafaelmfer.customviews.R

open class ActBase(
    val layout: Any? = R.layout.act_frame,
    val exceptionHandler: Class<out Thread.UncaughtExceptionHandler>? = null
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exceptionHandler?.let {
            Thread.setDefaultUncaughtExceptionHandler(it.newInstance())
        }
        when (layout) {
            is Int -> setContentView(layout)
            is View -> setContentView(layout)
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

    companion object {
        @JvmStatic
        lateinit var currentActivity: AppCompatActivity
    }
}
