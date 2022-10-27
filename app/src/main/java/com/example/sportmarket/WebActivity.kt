package com.example.sportmarket

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class WebActivity(
    private val activity: WebChromeActivity
) : WebChromeClient() {

    open class WebChromeActivity : androidx.appcompat.app.AppCompatActivity() {

        var callback: ValueCallback<Array<Uri?>?>? = null

        override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
            super.onCreate(savedInstanceState, persistentState)

            check()
        }

        override fun onActivityResult(code: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(code, resultCode, data)

            if (code == FILE_CHOOSER_RESULT_CODE && resultCode == RESULT_OK && data?.data != null) {
                val myRes: Array<Uri?>?

                myRes = arrayOf(data.data)

                this.callback?.onReceiveValue(myRes)

            } else {
                this.callback?.onReceiveValue(null)
            }
        }

        private fun check(){
            if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                1
            )
        }}
    }



    @SuppressLint("QueryPermissionsNeeded")
    override fun onShowFileChooser(
        view: WebView?,
        filePath: ValueCallback<Array<Uri?>?>,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        activity.callback = filePath

        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val intentArray: Array<Intent?> = arrayOf(galleryIntent)

        val contentSelection = Intent()
        contentSelection.action = Intent.ACTION_GET_CONTENT
        contentSelection.type = "image/*"
        contentSelection.addCategory(Intent.CATEGORY_OPENABLE)

        val myChooserContent = Intent(Intent.ACTION_CHOOSER)
        myChooserContent.putExtra(Intent.EXTRA_INTENT, contentSelection)
        myChooserContent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)

        activity.startActivityForResult(myChooserContent, INPUT_FILE_REQUEST_CODE)

        return true
    }

    companion object {
        const val INPUT_FILE_REQUEST_CODE = 1

        const val FILE_CHOOSER_RESULT_CODE = 1
    }
}