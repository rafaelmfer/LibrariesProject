package rafaelmfer.customviews.extensions.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

inline fun <reified BuilderViewBinding : RecyclerViewBuilderViewBinding<*, *>>
        RecyclerView.setupViewBinding(list: Collection<*>) =
    recyclerAdapterViewBinding<BuilderViewBinding>(list).apply { adapter = this }

inline fun <reified BuilderViewBinding : RecyclerViewBuilderViewBinding<*, *>> recyclerAdapterViewBinding(collection: Collection<*>) =
    object : RecyclerAdapter<RecyclerViewHolderViewBinding>(collection) {

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
            RecyclerViewHolderViewBinding(
                BuilderViewBinding::class.java.newInstance().init(viewGroup, collection)
            )

        override fun getItemCount() = collection.size

        override fun onBindViewHolder(viewHolder: RecyclerViewHolderViewBinding, position: Int) =
            viewHolder.builder.bind(position)
    }

open class RecyclerViewHolderViewBinding(val builder: RecyclerViewBuilderViewBinding<*, *>) :
    RecyclerView.ViewHolder(builder.build())

abstract class RecyclerViewBuilderViewBinding<Data, Binding : ViewBinding> {

    abstract val bindClass: Class<Binding>

    lateinit var binding: Binding
    lateinit var collection: Collection<Data>
    lateinit var context: Context

    private lateinit var viewGroup: ViewGroup

    @Suppress("UNCHECKED_CAST")
    fun init(viewGroup: ViewGroup, collection: Collection<*>): RecyclerViewBuilderViewBinding<Data, Binding> {
        this.viewGroup = viewGroup
        this.collection = collection as Collection<Data>
        context = viewGroup.context
        return this
    }

    fun build(): View {
        binding = inflate()
        binding.root.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    fun inflate() =
        bindClass.getMethod("inflate", LayoutInflater::class.java).invoke(
            null, ((viewGroup.context) as AppCompatActivity).layoutInflater
        ) as Binding


    fun bind(position: Int) = binding.onBind(position)

    abstract fun Binding.onBind(position: Int)
}