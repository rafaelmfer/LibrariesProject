package rafaelmfer.librariesproject

import android.os.Handler
import rafaelmfer.customviews.baseviews.ActBind
import rafaelmfer.customviews.buttons.LoadingButton
import rafaelmfer.librariesproject.databinding.ActLoadingButtonBinding

class ActLoadingButton : ActBind<ActLoadingButtonBinding>() {

    override val bindClass: Class<ActLoadingButtonBinding>
        get() = ActLoadingButtonBinding::class.java

    override fun ActLoadingButtonBinding.onBoundView() {
        loadingButton.set3secondsLoading()
        loadingButton2.set3secondsLoading()
        loadingButton3.set3secondsLoading()
    }

    private fun LoadingButton.set3secondsLoading() {
        setOnClickListener {
            setLoadingState(true)
            Handler().postDelayed({ setLoadingState(false) }, 3000)
        }
    }

}
