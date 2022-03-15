package com.example.makeup.util.extensions

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.makeup.R
import com.example.makeup.ui.adapters.FavoriteProductsAdapter
import com.example.makeup.data.database.entities.ProductsEntity
import com.example.makeup.data.database.entities.FavoritesEntity
import com.example.makeup.data.models.Products
import com.example.makeup.data.models.ProductsItem
import com.example.makeup.ui.products.ProductsFragmentDirections
import com.example.makeup.util.NetworkResult
import org.jsoup.Jsoup
import java.lang.Exception

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
    observe(lifecycleOwner, object : Observer<T>{
        override fun onChanged(t: T?) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}


