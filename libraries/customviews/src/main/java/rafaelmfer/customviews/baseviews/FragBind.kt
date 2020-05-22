package rafaelmfer.customviews.baseviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class FragBind<T : ViewBinding> : FragBase() {

    abstract val bindClass: Class<T>
    lateinit var binding: T

    @Suppress("UNCHECKED_CAST")
    fun inflate() =
        bindClass.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as T

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

    abstract fun T.onBoundView()
}


