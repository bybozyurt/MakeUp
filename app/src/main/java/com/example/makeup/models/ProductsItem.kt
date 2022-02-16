package com.example.makeup.models


import com.google.gson.annotations.SerializedName

data class ProductsItem(
    @SerializedName("api_featured_image")
    val apiFeaturedİmage: String?,
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_link")
    val imageLink: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("product_api_url")
    val productApiUrl: String?,
    @SerializedName("product_colors")
    val productColors: List<ProductColor>?,
    @SerializedName("product_link")
    val productLink: String?,
    @SerializedName("product_type")
    val productType: String?,
    @SerializedName("tag_list")
    val tagList: List<String>?,
    @SerializedName("website_link")
    val websiteLink: String?
)