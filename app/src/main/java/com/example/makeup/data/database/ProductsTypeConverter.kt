package com.example.makeup.data.database

import androidx.room.TypeConverter
import com.example.makeup.models.Products
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductsTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun foodRecipesToString(products: Products): String{
        return gson.toJson(products)
    }

    @TypeConverter
    fun stringToFoodRecipes(data: String): Products{
        val typeToken = object : TypeToken<Products>(){}.type
        return gson.fromJson(data, typeToken)
    }

}