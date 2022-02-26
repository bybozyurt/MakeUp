package com.example.makeup.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.makeup.R
import com.example.makeup.adapters.FavoriteProductsAdapter
import com.example.makeup.data.database.entities.FavoritesEntity

class FavoriteProductsBinding {

    companion object {

        fun setVisibility(view: View, favoritesEntity: List<FavoritesEntity>?, mAdapter: FavoriteProductsAdapter?) {
            when (view) {
                is RecyclerView -> {
                    val dataCheck = favoritesEntity.isNullOrEmpty()
                    view.isInvisible = dataCheck
                    if(!dataCheck){
                        favoritesEntity?.let { mAdapter?.setData(it) }
                    }
                }
                else -> view.isVisible = favoritesEntity.isNullOrEmpty()
            }
        }

    }

}