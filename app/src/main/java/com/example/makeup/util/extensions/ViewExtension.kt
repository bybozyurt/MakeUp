package com.example.makeup.util.extensions

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.makeup.R
import com.example.makeup.data.database.entities.FavoritesEntity
import com.example.makeup.data.database.entities.ProductsEntity
import com.example.makeup.data.models.Products
import com.example.makeup.data.models.ProductsItem
import com.example.makeup.ui.adapters.FavoriteProductsAdapter
import com.example.makeup.ui.products.ProductsFragmentDirections
import com.example.makeup.util.NetworkResult
import org.jsoup.Jsoup
import java.lang.Exception

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.setVisibility(favoritesEntity: List<FavoritesEntity>?, mAdapter: FavoriteProductsAdapter?){
    when (this) {
        is RecyclerView -> {
            val dataCheck = favoritesEntity.isNullOrEmpty()
            this.isInvisible = dataCheck
            if(!dataCheck){
                favoritesEntity?.let { mAdapter?.setData(it) }
            }
        }
        else -> this.isVisible = favoritesEntity.isNullOrEmpty()
    }
}

fun View.handleReadDataErrors(
    apiResponse: NetworkResult<Products>?,
    database: List<ProductsEntity>?
){
    when (this){
        is ImageView ->{
            this.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
        }
        is TextView ->{
            this.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
            this.text = apiResponse?.message.toString()
        }
    }
}

fun ImageView.loadImageFromUrl(imageUrl: String){
    this.load(imageUrl){
        crossfade(600)
        error(R.drawable.ic_sad)
    }
}

fun TextView.parseHtml(description: String?){
    description?.let {
        val desc = Jsoup.parse(it).text()
        this.text = desc
    }
}

fun ConstraintLayout.onProductClickListener(productsItem: ProductsItem){
    this.setOnClickListener {
        try {
            val action = ProductsFragmentDirections.actionProductsFragmentToDetailsActivity(productsItem)
            this.findNavController().navigate(action)
        }catch (e: Exception){
            Log.e("onProductClickListener",e.toString())
        }
    }
}