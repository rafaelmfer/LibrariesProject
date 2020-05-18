package rafaelmfer.librariesproject

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import rafaelmfer.customviews.baseviews.FragBind
import rafaelmfer.customviews.extensions.recyclerview.setupViewBinding
import rafaelmfer.librariesproject.databinding.FragHostBinding
import kotlin.reflect.KClass


class FragHost : FragBind<FragHostBinding>(FragHostBinding::class.java) {

    private val actList = listOf<KClass<out AppCompatActivity>>()

    override fun FragHostBinding.onBoundView() {

        componentsRecycler.apply {
            setupViewBinding<ComponentsViewBuilderBinding>(actList)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }

    }
}

