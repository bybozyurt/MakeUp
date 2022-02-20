package com.example.makeup.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
    val productId: Int,
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
): Parcelable