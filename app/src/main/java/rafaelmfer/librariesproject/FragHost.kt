package rafaelmfer.librariesproject

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import rafaelmfer.customviews.baseviews.ActBase
import rafaelmfer.customviews.baseviews.FragBind
import rafaelmfer.customviews.extensions.recyclerview.setupViewBinding
import rafaelmfer.librariesproject.databinding.FragHostBinding
import kotlin.reflect.KClass

class FragHost : FragBind<FragHostBinding>() {

    override val bindClass: Class<FragHostBinding>
        get() = FragHostBinding::class.java

    private val actList = listOf<KClass<out AppCompatActivity>>(
        ActLoadingButton::class,
        ActDotsProgressBar::class,
        ActMaterialIcons::class
    )

    override fun FragHostBinding.onBoundView() {

        componentsRecycler.apply {
            setupViewBinding<ComponentsViewBuilderBinding>(actList)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }
    }
}

class ActDotsProgressBar : ActBase(R.layout.act_dots_progress_bar)

class ActMaterialIcons : ActBase(R.layout.act_material_icons)