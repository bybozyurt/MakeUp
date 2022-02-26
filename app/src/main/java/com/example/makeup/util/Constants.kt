package com.example.makeup.util

class Constants {

    companion object {
        const val BASE_URL = "http://makeup-api.herokuapp.com"

        //API QUERY
        const val QUERY_BRAND = "brand"
        const val QUERY_PRODUCT_TYPE = "product_type"
        const val QUERY_TAGS= "product_tags"

        // ROOM Database
        const val DATABASE_NAME = "products_database"
        const val PRODUCTS_TABLE = "products_table"
        const val FAVORITE_PRODUCTS_TABLE = "favorite_products_table"

        //BottomSheet and Preferences
        const val DEFAULT_BRAND = "l'oreal"
        const val DEFAULT_CATEGORY = "lipstick"
        const val DEFAULT_TAGS = "Vegan"

        const val PREFERENCES_NAME = "makeup_preferences"
        const val PREFERENCES_BRAND = "brand"
        const val PREFERENCES_BRAND_ID = "brandId"
        const val PREFERENCES_CATEGORY = "category"
        const val PREFERENCES_CATEGORY_ID = "categoryId"
        const val PREFERENCES_TAG = "tags"
        const val PREFERENCES_TAG_ID = "tagsId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"
        const val PREFERENCES_CHECKED_CONTROL = "checkedControl"

        const val PRODUCTS_BUNDLE_KEY = "productsBundle"
        const val OVERVIEW_FRAGMENT = "Overview"
        const val WEBSITE_FRAGMENT = "Website"
        const val COLORS_FRAGMENT = "Colors"




    }


}