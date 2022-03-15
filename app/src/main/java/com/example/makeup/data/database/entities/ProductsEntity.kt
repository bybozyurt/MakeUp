package com.example.makeup.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.makeup.data.models.Products
import com.example.makeup.util.Constants.Companion.PRODUCTS_TABLE

@Entity(tableName = PRODUCTS_TABLE)
class ProductsEntity(
    var products: Products
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}