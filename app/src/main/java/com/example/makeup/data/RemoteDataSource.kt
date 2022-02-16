package com.example.makeup.data

import com.example.makeup.data.network.ProductsApi
import com.example.makeup.models.Products
import com.example.makeup.models.ProductsItem
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val productsApi: ProductsApi) {

    suspend fun getProducts(queries: Map<String, String>): Response<Products> {
        return productsApi.getProducts(queries)
    }
}