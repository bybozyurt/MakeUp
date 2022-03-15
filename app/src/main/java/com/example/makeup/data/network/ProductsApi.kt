package com.example.makeup.data.network

import com.example.makeup.data.models.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ProductsApi {

    @GET("/api/v1/products.json")
    suspend fun getProducts(
        @QueryMap queries: Map<String, String>
    ): Response<Products>

}