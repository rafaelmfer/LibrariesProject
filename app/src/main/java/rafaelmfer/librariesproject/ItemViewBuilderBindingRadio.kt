package rafaelmfer.librariesproject

import rafaelmfer.customviews.extensions.get
import rafaelmfer.customviews.extensions.recyclerview.ItemViewBuilderViewBinding
import rafaelmfer.customviews.extensions.recyclerview.recyclerAdapter
import rafaelmfer.librariesproject.databinding.ItemRadioBinding

class ItemViewBuilderBindingRadio : ItemViewBuilderViewBinding<String, ItemRadioBinding>() {

    override val bindClass: Class<ItemRadioBinding>
        get() = ItemRadioBinding::class.java

    companion object {
        var lastSelectionPosition = 0
    }
    override fun ItemRadioBinding.onBind(position: Int) {
        collection.get(position).run {
            radioExample.text = this

            radioExample.isChecked = lastSelectionPosition == position

            radioExample.setOnClickListener {
                lastSelectionPosition = position
                recycler.recyclerAdapter?.notifyDataSetChanged()
            }
        }
    }
}
