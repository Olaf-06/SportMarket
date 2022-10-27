package com.example.sportmarket.recycler_view

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.PackageManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sportmarket.R
import com.example.sportmarket.entity.Product
import java.io.*

class DataAdapterProduct(private val data: MutableList<Product>, private val productIMGs: MutableList<Bitmap>)
    : RecyclerView.Adapter<DataAdapterProduct.ViewHolderProduct>() {

    inner class ViewHolderProduct(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        var textName: TextView = itemView.findViewById(R.id.tvName)
        var textDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProduct {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent, false)
        return ViewHolderProduct(view)
    }

    override fun onBindViewHolder(holder: ViewHolderProduct, position: Int) {

        holder.imageProduct.setImageBitmap(productIMGs[position])
        holder.textName.text = data[position].name
        holder.textDescription.text = data[position].description
    }

    override fun getItemCount(): Int {

        return data.size
    }
}