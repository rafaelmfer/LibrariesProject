package rafaelmfer.librariesproject

import rafaelmfer.customviews.baseviews.ActBind
import rafaelmfer.customviews.databinding.ActFrameBinding
import rafaelmfer.customviews.dialog.newPanel

class ActPanel : ActBind<ActFrameBinding>() {

    override val bindClass by lazy { ActFrameBinding::class.java }

    override fun ActFrameBinding.onBoundView() {
        actFrameContainer.apply {
//            addView()
            newPanel { }
        }
    }
}
