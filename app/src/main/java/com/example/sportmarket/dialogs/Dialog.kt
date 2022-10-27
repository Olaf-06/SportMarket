package com.example.sportmarket.dialogs

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.sportmarket.MainActivity.Companion.COUNT_OF_DATA
import com.example.sportmarket.MainActivity.Companion.db
import com.example.sportmarket.MainActivity.Companion.editor
import com.example.sportmarket.MainActivity.Companion.router
import com.example.sportmarket.MainActivity.Companion.sharedPreferences
import com.example.sportmarket.R
import com.example.sportmarket.cicerone_navigation.Screens
import com.example.sportmarket.entity.Product
import com.example.sportmarket.fragments.ProductFragment.Companion.adapter
import com.example.sportmarket.room.Dao
import java.io.*

class Dialog : DialogFragment(), View.OnClickListener {

    private lateinit var imgButtonDialog: ImageButton
    private lateinit var etTitleDialog: EditText
    private lateinit var etDescriptionDialog: EditText
    private lateinit var btnAddDialog: Button

    private var selectedImage: Bitmap? = null

    private val dao: Dao? = db.dao()

    private var picAdded: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_product_dialog, container, false)
        etTitleDialog = view.findViewById(R.id.etTitleDialog)
        etDescriptionDialog = view.findViewById(R.id.etDescriptionDialog)

        btnAddDialog = view.findViewById(R.id.btnAddDialog)
        btnAddDialog.setOnClickListener(this)

        imgButtonDialog = view.findViewById(R.id.imgButtonDialog)
        imgButtonDialog.setOnClickListener(this)

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imgButtonDialog -> {
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, 1)
                picAdded = true
            }
            R.id.btnAddDialog -> {

                val countOfData: Int = sharedPreferences.getInt(COUNT_OF_DATA, 1)

                val title = etTitleDialog.text.toString()
                val description = etDescriptionDialog.text.toString()

                if (picAdded) {
                    selectedImage?.let { saveImage(it, "$countOfData") }
                    val product = Product(countOfData, title, description)
                    editor.putInt(COUNT_OF_DATA, countOfData + 1).apply()
                    dao?.insert(product)
                    router.navigateTo(Screens.productFragment())
                    dismiss()

                } else {
                    Toast.makeText(context, "Добавьте изображение!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> try {
                val imageStream: InputStream? = data?.data?.let {
                    context?.contentResolver?.openInputStream(it)
                }
                selectedImage = BitmapFactory.decodeStream(imageStream)
                imgButtonDialog.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun saveImage(image: Bitmap, fileName: String) {
        val imagesFolder = File(context?.cacheDir, "images")
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, fileName)
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
        }
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }
}