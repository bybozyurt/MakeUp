package com.example.makeup.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductColor(
    @SerializedName("colour_name")
    val colourName: String?,
    @SerializedName("hex_value")
    val hexValue: String?
): Parcelable