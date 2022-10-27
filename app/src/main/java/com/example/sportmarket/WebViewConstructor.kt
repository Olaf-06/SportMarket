package com.example.sportmarket

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.webkit.*

class WebViewConstructor {

    @SuppressLint("StaticFieldLeak")
    var instance: WebView? = null
    val cookies = CookieManager.getInstance()

                             /**
                              *  Ссылка меняется здесь
                              */
    var urlBeforeRedirect: String? = "http://www.vk.com"
    val chromeWebViewClient by lazy {
        object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                cookies.setAcceptThirdPartyCookies(view, true)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                cookies.setAcceptThirdPartyCookies(view, true)
                CookieSyncManager.getInstance().sync();
                val firstUrl = Uri.parse(url).host
                val secondUrl = Uri.parse(urlBeforeRedirect).host
                if (firstUrl != secondUrl && firstUrl != "" && secondUrl != "") {
                    view?.visibility = View.VISIBLE
                }
            }
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    fun apply(webSettings: WebSettings) {
        webSettings.apply {
            useWideViewPort = true
            javaScriptCanOpenWindowsAutomatically = true
            databaseEnabled = true
            domStorageEnabled = true
            javaScriptEnabled = true
            displayZoomControls = true
            cacheMode = WebSettings.LOAD_DEFAULT
            allowContentAccess = true

        }
    }
}