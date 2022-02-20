package com.example.makeup.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.example.makeup.R
import com.example.makeup.data.Repository
import com.example.makeup.data.database.ProductsEntity
import com.example.makeup.models.Products
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
    val readProducts2 : LiveData<List<ProductsEntity>> = readProducts

    private fun insertProducts(productsEntity: ProductsEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(productsEntity)
        }

    var productsResponse: MutableLiveData<NetworkResult<Products>> = MutableLiveData()
    var productsResponse2: MutableLiveData<NetworkResult<Products>> = productsResponse
    var searchedProductsResponse: MutableLiveData<NetworkResult<Products>> = MutableLiveData()

    val pairMediatorLiveData = PairMediatorLiveData(productsResponse2, readProducts2)

    fun getProducts(queries: Map<String, String>) = viewModelScope.launch {
        getProductsSafeCall(queries)
    }

    fun getSearchProducts(queries: Map<String, String>) = viewModelScope.launch {
        searchProductsSafeCall(queries)
    }

    private suspend fun searchProductsSafeCall(queries: Map<String, String>) {
        searchedProductsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchProducts(queries)
                searchedProductsResponse.value = handleProductsResponse(response)

            } catch (e: Exception) {
                searchedProductsResponse.value = NetworkResult.Error("Recipes not found")
            }
        } else {
            searchedProductsResponse.value = NetworkResult.Error("No Internet Connection")
        }

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
                    offlineCacheRecipes(makeUp)
                }
            } catch (e: Exception) {
                productsResponse.value = NetworkResult.Error("Products not found.")
            }
        } else {
            productsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    //room
    private fun offlineCacheRecipes(foodRecipe: Products) {
        val productsEntity = ProductsEntity(foodRecipe)
        insertProducts(productsEntity)
    }

    private fun handleProductsResponse(response: Response<Products>): NetworkResult<Products>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.body()!!.isNullOrEmpty() -> {
                return NetworkResult.Error("Products Not Found")
            }
            response.isSuccessful -> {
                val products = response.body()
                return NetworkResult.Success(products!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
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