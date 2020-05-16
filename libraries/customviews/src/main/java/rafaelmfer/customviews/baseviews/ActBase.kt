package rafaelmfer.customviews.baseviews

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

open class ActBase(
    val layout: Any? = null,
    val exceptionHandler: Class<out Thread.UncaughtExceptionHandler>? = null
) : AppCompatActivity() {

    open var viewModel: ViewModel? = null

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
        viewModel?.onViewModel()
        onView()
    }

    override fun onResume() {
        super.onResume()
        currentActivity = this
    }

    open fun Bundle.onExtras() {}

    open fun ViewModel.onViewModel() {}

    open fun onView() {}

    companion object {
        const val teste = "string"

        @JvmStatic
        lateinit var currentActivity: AppCompatActivity
    }

}
