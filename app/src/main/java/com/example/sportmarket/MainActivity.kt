package com.example.sportmarket

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.sportmarket.room.Database
import com.example.sportmarket.cicerone_navigation.MyRouter
import com.example.sportmarket.cicerone_navigation.Screens
import com.example.sportmarket.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator


class MainActivity : WebActivity.WebChromeActivity() {

    companion object {
        internal lateinit var router: Router
        internal lateinit var sharedPreferences: SharedPreferences
        internal lateinit var editor: SharedPreferences.Editor
        internal lateinit var db: Database

        internal const val COUNT_OF_DATA = "countOfData"
        internal const val SHARED_PREFERENCES = "sharedPreferences"
    }

    private val myRouter = MyRouter()
    private val navigator = AppNavigator(this, R.id.container)

    private val webViewConstructor = WebViewConstructor()

    val webChromeClient by lazy {
        WebActivity(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE).also {
            editor = it.edit()
        }

        db = App.instance.database

        router = myRouter.router().also { it.newRootScreen(Screens.productFragment()) }

        if (hasConnection(this)) {
            webViewConstructor.instance = MyWebView(this)
                .apply(binding, this, webViewConstructor)
        }
    }

    override fun onResume() {
        super.onResume()
        myRouter.navigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        myRouter.navigatorHolder().removeNavigator()
    }

    fun hasConnection(context: Context): Boolean {
        val cm =
            context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        var wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.activeNetworkInfo
        return wifiInfo != null && wifiInfo.isConnected
    }

    override fun onBackPressed() {
        if (webViewConstructor.instance is WebView) {
            webViewConstructor.instance?.goBack()
        } else {
            super.onBackPressed()
        }
    }
}