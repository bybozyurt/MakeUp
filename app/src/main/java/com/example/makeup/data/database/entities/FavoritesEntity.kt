package com.example.makeup.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.makeup.data.models.ProductsItem
import com.example.makeup.util.Constants.Companion.FAVORITE_PRODUCTS_TABLE

@Entity(tableName = FAVORITE_PRODUCTS_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var productsItem: ProductsItem
) {
}