package com.example.sportmarket

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.webkit.CookieSyncManager
import android.webkit.WebView
import android.widget.LinearLayout
import com.example.sportmarket.databinding.ActivityMainBinding

class MyWebView(context: Context) : WebView(context) {

    @SuppressLint("ResourceType")
    fun apply(
        binding: ActivityMainBinding,
        mainActivity: MainActivity,
        webViewConstructor: WebViewConstructor
    )
    : WebView {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        binding.root.addView(this)
        id = 345345
        bringToFront()
        z = 10f
        visibility = View.GONE
        webViewConstructor.cookies.setAcceptThirdPartyCookies(this, true)
        CookieSyncManager.getInstance().sync()
        webViewClient = webViewConstructor.chromeWebViewClient
        webChromeClient = mainActivity.webChromeClient
        webViewConstructor.apply(this.settings)
        webViewConstructor.urlBeforeRedirect?.let { loadUrl(it) }
        return this
    }
}