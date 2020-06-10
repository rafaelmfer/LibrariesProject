package rafaelmfer.customviews.baseviews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

abstract class ActBind<Binding : ViewBinding> : ActBase() {

    abstract val bindClass: Class<Binding>
    lateinit var binding: Binding

    @Suppress("UNCHECKED_CAST")
    fun inflate() =
        bindClass.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate()
        setContentView(binding.root)
        binding.onBoundView()
    }

    open fun Binding.onBoundView() {}

    inline fun <reified B : ViewBinding> viewBind() = lazy { bindView(B::class) }
}

val Context.inflater get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

@Suppress("UNCHECKED_CAST")
fun <B : ViewBinding> Context.bindView(kClass: KClass<B>) =
    kClass.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, inflater) as B
