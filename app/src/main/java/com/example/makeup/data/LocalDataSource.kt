package com.example.makeup.data

import com.example.makeup.data.database.ProductsDao
import com.example.makeup.data.database.ProductsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val productsDao: ProductsDao
) {

    fun readDatabase(): Flow<List<ProductsEntity>> {
        return productsDao.readRecipes()
    }

    suspend fun insertRecipes(productsEntity: ProductsEntity) {
        productsDao.insertRecipes(productsEntity)
    }

}