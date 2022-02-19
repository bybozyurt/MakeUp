package com.example.makeup.data.network

import com.example.makeup.models.Products
import com.example.makeup.models.ProductsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ProductsApi {

    @GET("/api/v1/products.json")
    suspend fun getProducts(
        @QueryMap queries: Map<String, String>
    ): Response<Products>

    @GET("/api/v1/products.json")
    suspend fun searchProducts(
        @QueryMap searchQuery: Map<String, String>
    ): Response<Products>

}