package rafaelmfer.customviews.baseviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class FragBind<Binding : ViewBinding> : FragBase() {

    abstract val bindClass: Class<Binding>
    lateinit var binding: Binding

    @Suppress("UNCHECKED_CAST")
    fun inflate() =
        bindClass.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        state: Bundle?
    ): View? {
        binding = inflate()
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onBoundView()
    }

    abstract fun Binding.onBoundView()

    inline fun <reified B : ViewBinding> viewBind() =
        lazy { requireContext().bindView(B::class) }
}


