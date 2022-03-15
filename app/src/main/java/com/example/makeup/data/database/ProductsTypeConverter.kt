package com.example.makeup.data.database

import androidx.room.TypeConverter
import com.example.makeup.data.models.Products
import com.example.makeup.data.models.ProductsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductsTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun productsToString(products: Products): String{
        return gson.toJson(products)
    }

    @TypeConverter
    fun stringToProducts(data: String): Products{
        val typeToken = object : TypeToken<Products>(){}.type
        return gson.fromJson(data, typeToken)
    }

    @TypeConverter
    fun productsItemToString(productsItem: ProductsItem): String {
        return gson.toJson(productsItem)
    }

    @TypeConverter
    fun stringToProductsItem(data: String): ProductsItem {
        val listType = object : TypeToken<ProductsItem>() {}.type
        return gson.fromJson(data, listType)
    }

}