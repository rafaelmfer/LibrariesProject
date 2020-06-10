package rafaelmfer.customviews.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

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

@Suppress("DEPRECATION")
inline fun <reified Model : ViewModel> FragmentActivity.viewModel(): Lazy<Model> = lazy {
    ViewModelProviders.of(this).get(Model::class.java)
}

@Suppress("DEPRECATION")
inline fun <reified Model : ViewModel> Fragment.viewModel(): Lazy<Model> = lazy {
    ViewModelProviders.of(this).get(Model::class.java)
}