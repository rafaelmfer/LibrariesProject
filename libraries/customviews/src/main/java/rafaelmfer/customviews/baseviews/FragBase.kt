package rafaelmfer.customviews.baseviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class FragBase(private val layout: Int? = null) : Fragment() {

    val activityContract by lazy { activity as ActivityContract }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?) =
        layout?.let { inflater.inflate(it, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.onArguments()
        view.onView()
    }

    open fun Bundle.onArguments() {}

    open fun View.onView() {}

    companion object {
        inline fun <reified T : FragBase> new(bundle: Bundle?): T =
            T::class.java.newInstance().apply { arguments = bundle }
    }
}