package rafaelmfer.librariesproject

import androidx.appcompat.app.AppCompatActivity
import rafaelmfer.customviews.extensions.get
import rafaelmfer.customviews.extensions.onClickStart
import rafaelmfer.customviews.extensions.recyclerview.ItemViewBuilderViewBinding
import rafaelmfer.librariesproject.databinding.ItemHostBinding
import kotlin.reflect.KClass

class ComponentsViewBuilderBinding : ItemViewBuilderViewBinding<KClass<out AppCompatActivity>, ItemHostBinding>() {

    override val bindClass: Class<ItemHostBinding>
        get() = ItemHostBinding::class.java

    override fun ItemHostBinding.onBind(position: Int) {
        collection.get(position).run {
            componentButton.text = simpleName
            componentButton.onClickStart(this)
        }
    }
}
