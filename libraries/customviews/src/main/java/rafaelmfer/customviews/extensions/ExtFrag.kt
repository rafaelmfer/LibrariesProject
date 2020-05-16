package rafaelmfer.customviews.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment

fun <T : Fragment> T.newInstance(bundle: Bundle? = null): T {
    arguments = bundle
    return this
}

fun <T : Fragment> T.newInstance(bundleBuilder: Bundle.() -> Unit) = apply {
    Bundle().let {
        bundleBuilder.invoke(it)
        arguments = it
    }
}

fun Fragment.requireAct(block: Fragment.() -> Unit) =
    requireActivity().run { block.invoke(this@requireAct) }

//inline fun <reified T : ViewModel> Fragment.viewModel(): T =
//    ViewModelProviders.of(this).get(T::class.java)
