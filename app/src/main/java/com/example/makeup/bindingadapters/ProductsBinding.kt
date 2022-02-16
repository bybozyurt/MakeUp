package com.example.makeup.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.makeup.data.database.ProductsEntity
import com.example.makeup.models.Products
import com.example.makeup.util.NetworkResult

class ProductsBinding {

    companion object{

        fun handleReadDataErrors(
            view: View,
            apiResponse: NetworkResult<Products>?,
            database: List<ProductsEntity>?
        ){
            when (view){
                is ImageView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                }
                is TextView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                    view.text = apiResponse?.message.toString()
                }
            }
        }

    }
}