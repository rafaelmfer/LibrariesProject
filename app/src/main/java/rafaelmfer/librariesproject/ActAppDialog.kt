package rafaelmfer.librariesproject

import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import rafaelmfer.customviews.baseviews.ActBase
import rafaelmfer.customviews.dialog.AppDialog
import rafaelmfer.customviews.extensions.toast

class ActAppDialog : ActBase() {

    override fun ViewGroup.onView() {
        findViewById<FrameLayout>(R.id.act_frame_container).addView(Button(this@ActAppDialog).apply {
            text = "open Dialog"
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
            setOnClickListener {

                AppDialog(this@ActAppDialog) {
                    title.text = getString(R.string.ricota)
                    message.text = getString(R.string.ricota_desc)
                    positive.text = getString(R.string.adoro)
                    negative.text = getString(R.string.nao_gosto)
                    positive.setOnClickListener {
                        toast("SIM!")
                        dismiss()
                    }
                    negative.setOnClickListener {
                        toast("NÃO!")
                        dismiss()
                    }
                }
            }
        })
    }
}
