package com.example.makeup.data.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductsItem(
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val productId: Int,
    @SerializedName("image_link")
    val imageLink: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("product_colors")
    val productColors: List<ProductColor>?,
    @SerializedName("product_link")
    val productLink: String?,
    @SerializedName("product_type")
    val productType: String?,
    @SerializedName("tag_list")
    val tagList: List<String>?
): Parcelable