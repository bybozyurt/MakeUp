package com.example.makeup.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.makeup.data.database.entities.FavoritesEntity
import com.example.makeup.data.database.entities.ProductsEntity

@Database(
    entities = [ProductsEntity::class, FavoritesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ProductsTypeConverter::class)
abstract class ProductsDatabase: RoomDatabase(){

    abstract fun productsDao(): ProductsDao

}