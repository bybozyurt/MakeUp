package com.example.makeup.util

class Constants {

    companion object {
        const val BASE_URL = "http://makeup-api.herokuapp.com"

        //API QUERY
        const val QUERY_BRAND = "brand"
        const val QUERY_PRODUCT_TYPE = "product_type"

        // ROOM Database
        const val DATABASE_NAME = "products_database"
        const val PRODUCTS_TABLE = "products_table"

        //BottomSheet and Preferences
        const val DEFAULT_BRAND = "dior"
        const val DEFAULT_CATEGORY = "lipstick"

        const val PREFERENCES_NAME = "makeup_preferences"
        const val PREFERENCES_BRAND = "brand"
        const val PREFERENCES_BRAND_ID = "brandId"
        const val PREFERENCES_CATEGORY = "category"
        const val PREFERENCES_CATEGORY_ID = "categoryId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"

    }


}