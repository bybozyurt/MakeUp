package com.example.makeup.data.database

import androidx.room.*
import com.example.makeup.data.database.entities.FavoritesEntity
import com.example.makeup.data.database.entities.ProductsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(productsEntity: ProductsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteProducts(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM products_table ORDER BY id ASC")
    fun readProducts(): Flow<List<ProductsEntity>>

    @Query("SELECT * FROM favorite_products_table ORDER BY id ASC")
    fun readFavoriteProducts(): Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavoriteProduct(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorite_products_table")
    suspend fun deleteAllFavoriteProducts()


}