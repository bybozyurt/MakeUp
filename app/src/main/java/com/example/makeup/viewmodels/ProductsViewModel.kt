package com.example.makeup.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.makeup.util.extensions.toast
import com.example.makeup.data.BrandAndCategory
import com.example.makeup.data.DataStoreRepository
import com.example.makeup.util.Constants.Companion.DEFAULT_BRAND
import com.example.makeup.util.Constants.Companion.DEFAULT_CATEGORY
import com.example.makeup.util.Constants.Companion.QUERY_BRAND
import com.example.makeup.util.Constants.Companion.QUERY_PRODUCT_TYPE
import com.example.makeup.util.Constants.Companion.QUERY_TAGS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private lateinit var brandAndCategory: BrandAndCategory

    var networkStatus = false
    var backOnline = false

    val readBrandAndCategory = dataStoreRepository.readBrandAndCategory
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    //if successful response triggered this fun
    fun saveBrandAndCategory() =
        viewModelScope.launch(Dispatchers.IO) {
            if (this@ProductsViewModel::brandAndCategory.isInitialized) {
                dataStoreRepository.saveBrandAndCategory(
                    brandAndCategory.selectedBrand,
                    brandAndCategory.selectedBrandId,
                    brandAndCategory.selectedCategory,
                    brandAndCategory.selectedCategoryId,
                    brandAndCategory.selectedTag,
                    brandAndCategory.selectedTagId,
                    brandAndCategory.checkedControl
                )
            }
        }

    fun saveBrandAndCategoryTemp(
        brand: String,
        brandId: Int,
        category: String,
        categoryId: Int,
        tag: String,
        tagId: Int,
        checkedControl: Boolean,
    ) {

        brandAndCategory =
            BrandAndCategory(brand, brandId, category, categoryId, tag, tagId, checkedControl)

    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()


        if (this@ProductsViewModel::brandAndCategory.isInitialized) {
            if (!brandAndCategory.checkedControl) {
                queries[QUERY_BRAND] = brandAndCategory.selectedBrand
                queries[QUERY_PRODUCT_TYPE] = brandAndCategory.selectedCategory
                Log.e(
                    "şahinbaba",
                    brandAndCategory.selectedBrand + "-----" + brandAndCategory.selectedCategory
                )
            } else {
                queries[QUERY_TAGS] = brandAndCategory.selectedTag
            }
        } else {
            queries[QUERY_BRAND] = DEFAULT_BRAND
            queries[QUERY_PRODUCT_TYPE] = DEFAULT_CATEGORY
        }

        return queries
    }

    fun saveBackOnline(backOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            getApplication<Application>().toast("No Internet Connection")
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                getApplication<Application>().toast("Internet Activated")
                saveBackOnline(false)
            }
        }
    }
}