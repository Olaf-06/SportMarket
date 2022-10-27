package com.example.sportmarket.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sportmarket.entity.Product

@Database(entities = [Product::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao?
}