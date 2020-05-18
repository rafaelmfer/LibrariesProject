package rafaelmfer.librariesproject

import rafaelmfer.customviews.baseviews.ActBind
import rafaelmfer.customviews.baseviews.ActivityContract
import rafaelmfer.customviews.baseviews.FragBase.Companion.new
import rafaelmfer.customviews.databinding.ActFrameBinding
import rafaelmfer.customviews.extensions.replaceFragment
import rafaelmfer.customviews.extensions.setStatusBarColor

class ActHost(override val container: Int = R.id.act_frame_container) :
    ActBind<ActFrameBinding>(ActFrameBinding::class.java), ActivityContract {

    override fun onResume() {
        super.onResume()
        replaceFragment(new<FragHost>(bundle = null), false)
    }

    override fun ActFrameBinding.onBoundView() {
        setStatusBarColor(R.color.blue_800, false)
    }
}
