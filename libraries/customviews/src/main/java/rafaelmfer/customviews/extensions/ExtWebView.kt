package rafaelmfer.customviews.extensions

import android.webkit.WebView
import android.webkit.WebViewClient

fun WebView.loadInApp(inApp: Boolean = true) {
    if (inApp) {
        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
    }
}