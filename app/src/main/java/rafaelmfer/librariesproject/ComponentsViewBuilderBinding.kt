package rafaelmfer.librariesproject

import androidx.appcompat.app.AppCompatActivity
import rafaelmfer.customviews.extensions.get
import rafaelmfer.customviews.extensions.onClickStart
import rafaelmfer.customviews.extensions.recyclerview.RecyclerViewBuilderViewBinding
import rafaelmfer.librariesproject.databinding.ItemHostBinding
import kotlin.reflect.KClass

class ComponentsViewBuilderBinding : RecyclerViewBuilderViewBinding<KClass<out AppCompatActivity>, ItemHostBinding>() {

    override val bindClass: Class<ItemHostBinding>
        get() = ItemHostBinding::class.java

    override fun ItemHostBinding.onBind(position: Int) {
        collection.get(position).run {
            componentButton.text = qualifiedName
            componentButton.onClickStart(this)
        }
    }
}
