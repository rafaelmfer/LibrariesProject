package rafaelmfer.customviews.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

interface LifeCycleContract<T> {
    val owner get() = this as LifecycleOwner
    var response: MutableList<T>
}

@Suppress("UNCHECKED_CAST")
fun <T> LiveData<T>.observe(lifeCycle: LifecycleOwner, callback: T.() -> Unit = {}): T? = (lifeCycle as LifeCycleContract<T>).run {
    var parcelable : T? = null
    observe(owner, Observer<T> {
        it?.run {
            response.add(this)
            callback.invoke(this)
            parcelable = this
        }
    })
    return parcelable
}
