package rafaelmfer.librariesproject

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.act_recycler_radio_button.*
import rafaelmfer.customviews.baseviews.ActBind
import rafaelmfer.customviews.extensions.recyclerview.setupViewBinding
import rafaelmfer.librariesproject.databinding.ActRecyclerRadioButtonBinding

class ActRecyclerRadioButton : ActBind<ActRecyclerRadioButtonBinding>() {

    override val bindClass get() = ActRecyclerRadioButtonBinding::class.java

    private val actList = listOf(
        "Exemplo 1",
        "Exemplo 2",
        "Exemplo 3",
        "Exemplo 4",
        "Exemplo 5",
        "Exemplo 6",
        "Exemplo 7"
    )

    override fun ActRecyclerRadioButtonBinding.onBoundView() {
        radio_list.apply {
            layoutManager = LinearLayoutManager(this@ActRecyclerRadioButton, LinearLayoutManager.VERTICAL, false)
            setupViewBinding<ItemViewBuilderBindingRadio>(actList)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}