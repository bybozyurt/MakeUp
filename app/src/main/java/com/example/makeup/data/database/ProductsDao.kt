package com.example.makeup.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: ProductsEntity)

    @Query("SELECT * FROM products_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<ProductsEntity>>
}