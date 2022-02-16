package com.example.makeup.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.makeup.util.Constants.Companion.QUERY_BRAND
import com.example.makeup.util.Constants.Companion.QUERY_PRODUCT_TYPE

class ProductsViewModel(application: Application): AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_BRAND] = "covergirl"
        queries[QUERY_PRODUCT_TYPE] = "lipstick"

        return queries
    }
}