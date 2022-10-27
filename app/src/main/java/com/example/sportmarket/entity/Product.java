package com.example.sportmarket.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey
    public int photoId;

    public String name;
    public String description;

    public Product(int photoId, String name, String description){
        this.name = name;
        this.description = description;
        this.photoId = photoId;
    }

}
