package com.example.sportmarket.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.sportmarket.MainActivity.Companion.db
import com.example.sportmarket.R
import com.example.sportmarket.dialogs.Dialog
import com.example.sportmarket.entity.Product
import com.example.sportmarket.recycler_view.DataAdapterProduct
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.*
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class ProductFragment : Fragment(), View.OnClickListener {

    private var products: MutableList<Product> = mutableListOf()
    private val productsIMGs: MutableList<Bitmap> = mutableListOf()

    private lateinit var fabAddProduct: FloatingActionButton

    private lateinit var dialogFragment: DialogFragment

    private lateinit var rvProduct: RecyclerView

    private lateinit var skeletonScreen: SkeletonScreen

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        fabAddProduct = view.findViewById<FloatingActionButton?>(R.id.fabAddProduct)
            .also { it.setOnClickListener(this@ProductFragment) }
        rvProduct = view.findViewById(R.id.recyclerView)

        dialogFragment = Dialog()

        products = db.dao()?.all ?: mutableListOf()

        for(a in products) {
            productsIMGs.add(getImage("${a.photoId}"))
        }

        rvProduct.layoutManager = LinearLayoutManager(this.activity)
        adapter = DataAdapterProduct(products, productsIMGs)
        rvProduct.adapter = adapter

        skeletonScreen = Skeleton.bind(rvProduct)
            .adapter(adapter)
            .duration(3000)
            .load(R.layout.item_product)
            .show()

        thread {
            sleep(3000)
            activity?.runOnUiThread {
                skeletonScreen.hide()
            }
        }
        return view
    }

    private fun getImage(fileName: String): Bitmap {
        val imagesFolder = File(context?.cacheDir, "images")

        imagesFolder.mkdirs()
        val file = File(imagesFolder, fileName)
        val stream = FileInputStream(file)
        return BitmapFactory.decodeStream(stream)

    }

    override fun onClick(p0: View?) {
        if(p0?.id == R.id.fabAddProduct) {
            fragmentManager?.let { dialogFragment.show(it, "dig1") }
        }
    }

    companion object{
        internal lateinit var adapter: DataAdapterProduct
    }
}