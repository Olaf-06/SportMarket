package com.example.sportmarket.room

import androidx.room.*
import androidx.room.Dao
import com.example.sportmarket.entity.Product

@Dao
interface Dao {
    @get:Query("SELECT * FROM product")
    val all: MutableList<Product>

    @Query("SELECT * FROM product WHERE photoId = :id")
    fun getById(id: Long): Product?

    @Insert
    fun insert(employee: Product?)

    @Update
    fun update(employee: Product?)

    @Delete
    fun delete(employee: Product?)
}