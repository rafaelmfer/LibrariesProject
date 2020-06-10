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

inline fun <reified BuilderViewBinding : ItemViewBuilderViewBinding<*, *>>
        RecyclerView.setupViewBinding(list: Collection<*>) =
    recyclerAdapterViewBinding<BuilderViewBinding>(list).apply { adapter = this }

inline fun <reified BuilderViewBinding : ItemViewBuilderViewBinding<*, *>> recyclerAdapterViewBinding(collection: Collection<*>) =
    object : RecyclerAdapter<RecyclerViewHolderViewBinding>(collection) {

        override var onTarget: () -> Unit = {}

        override fun getTarget() = collection.size - 10

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
            RecyclerViewHolderViewBinding(
                BuilderViewBinding::class.java.newInstance().init(viewGroup, collection)
            )

        override fun getItemCount() = collection.size

        override fun onBindViewHolder(viewHolder: RecyclerViewHolderViewBinding, position: Int) {
            if (position == getTarget()) {
                onTarget()
            }
            viewHolder.builder.onBind(position)
        }
    }

open class RecyclerViewHolderViewBinding(val builder: ItemViewBuilderViewBinding<*, *>) :
    RecyclerView.ViewHolder(builder.build())

abstract class ItemViewBuilderViewBinding<Data, Binding : ViewBinding> {

    abstract val bindClass: Class<Binding>

    lateinit var binding: Binding
    lateinit var collection: Collection<Data>
    lateinit var context: Context
    lateinit var recycler: RecyclerView

    @Suppress("UNCHECKED_CAST")
    fun init(group: ViewGroup, coll: Collection<*>) = apply {
        recycler = group as RecyclerView
        collection = coll as Collection<Data>
        context = group.context
    }

    fun build(): View {
        binding = inflate()
        binding.root.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    fun inflate() =
        bindClass.getMethod("inflate", LayoutInflater::class.java).invoke(
            null, ((context) as AppCompatActivity).layoutInflater
        ) as Binding


    fun onBind(position: Int) = binding.onBind(position)

    abstract fun Binding.onBind(position: Int)
}