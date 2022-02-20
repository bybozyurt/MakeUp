package com.example.makeup.data

import com.example.makeup.data.database.ProductsDao
import com.example.makeup.data.database.ProductsEntity
import com.example.makeup.data.database.entities.FavoritesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val productsDao: ProductsDao
) {

    fun readDatabase(): Flow<List<ProductsEntity>> {
        return productsDao.readProducts()
    }

    fun readFavoriteProducts(): Flow<List<FavoritesEntity>> {
        return productsDao.readFavoriteProducts()
    }

    suspend fun insertProducts(productsEntity: ProductsEntity) {
        productsDao.insertRecipes(productsEntity)
    }

    suspend fun insertFavoriteProducts(favoritesEntity: FavoritesEntity) {
        productsDao.insertFavoriteProducts(favoritesEntity)
    }

    suspend fun deleteFavoriteProduct(favoritesEntity: FavoritesEntity) {
        productsDao.deleteFavoriteProduct(favoritesEntity)
    }

    suspend fun deleteAllFavoriteProducts() {
        productsDao.deleteAllFavoriteProducts()
    }

}