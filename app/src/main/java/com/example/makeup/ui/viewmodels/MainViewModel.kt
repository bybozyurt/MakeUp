package com.example.makeup.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.makeup.data.Repository
import com.example.makeup.data.database.entities.ProductsEntity
import com.example.makeup.data.database.entities.FavoritesEntity
import com.example.makeup.data.models.Products
import com.example.makeup.util.NetworkResult
import com.example.makeup.util.PairMediatorLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    val readProducts: LiveData<List<ProductsEntity>> = repository.local.readDatabase().asLiveData()
    val readFavoriteProducts: LiveData<List<FavoritesEntity>> =
        repository.local.readFavoriteProducts().asLiveData()
    //val readProducts2: LiveData<List<ProductsEntity>> = readProducts

    private fun insertProducts(productsEntity: ProductsEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertProducts(productsEntity)
        }

    fun insertFavoriteProducts(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteProducts(favoritesEntity)
        }

    fun deleteFavoriteProduct(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteProduct(favoritesEntity)
        }

    fun deleteAllFavoriteProducts() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavoriteProducts()
        }

    var productsResponse: MutableLiveData<NetworkResult<Products>> = MutableLiveData()
    //var productsResponse2: MutableLiveData<NetworkResult<Products>> = productsResponse

    val pairMediatorLiveData = PairMediatorLiveData(productsResponse, readProducts)

    fun getProducts(queries: Map<String, String>) = viewModelScope.launch {
        getProductsSafeCall(queries)
    }


    private suspend fun getProductsSafeCall(queries: Map<String, String>) {
        productsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getProducts(queries)
                productsResponse.value = handleProductsResponse(response)
                val makeUp = productsResponse.value!!.data
                //room
                makeUp?.let {
                    offlineCacheProducts(makeUp)
                }
            } catch (e: Exception) {
                productsResponse.value = NetworkResult.Error("Products not found.")
            }
        } else {
            productsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    //room
    private fun offlineCacheProducts(products: Products) {
        val productsEntity = ProductsEntity(products)
        insertProducts(productsEntity)
    }

    private fun handleProductsResponse(response: Response<Products>): NetworkResult<Products>? {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.body()!!.isNullOrEmpty() -> {
                NetworkResult.Error("Products Not Found")
            }
            response.isSuccessful -> {
                val products = response.body()
                NetworkResult.Success(products!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}