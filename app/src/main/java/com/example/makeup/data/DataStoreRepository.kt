package com.example.makeup.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.makeup.util.Constants.Companion.DEFAULT_BRAND
import com.example.makeup.util.Constants.Companion.DEFAULT_CATEGORY
import com.example.makeup.util.Constants.Companion.DEFAULT_TAGS
import com.example.makeup.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.example.makeup.util.Constants.Companion.PREFERENCES_BRAND
import com.example.makeup.util.Constants.Companion.PREFERENCES_BRAND_ID
import com.example.makeup.util.Constants.Companion.PREFERENCES_CATEGORY
import com.example.makeup.util.Constants.Companion.PREFERENCES_CATEGORY_ID
import com.example.makeup.util.Constants.Companion.PREFERENCES_CHECKED_CONTROL
import com.example.makeup.util.Constants.Companion.PREFERENCES_NAME
import com.example.makeup.util.Constants.Companion.PREFERENCES_TAG
import com.example.makeup.util.Constants.Companion.PREFERENCES_TAG_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKey {
        val selectedBrand = stringPreferencesKey(PREFERENCES_BRAND)
        val selectedBrandId = intPreferencesKey(PREFERENCES_BRAND_ID)
        val selectedCategory = stringPreferencesKey(PREFERENCES_CATEGORY)
        val selectedCategoryId = intPreferencesKey(PREFERENCES_CATEGORY_ID)
        val selectedTag = stringPreferencesKey(PREFERENCES_TAG)
        val selectedTagId = intPreferencesKey(PREFERENCES_TAG_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
        val checkedControl = booleanPreferencesKey(PREFERENCES_CHECKED_CONTROL)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveBrandAndCategory(
        brand: String,
        brandId: Int,
        category: String,
        categoryId: Int,
        tag: String,
        tagId: Int,
        checkedControl: Boolean
    ) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.selectedBrand] = brand
            preferences[PreferencesKey.selectedBrandId] = brandId
            preferences[PreferencesKey.selectedCategory] = category
            preferences[PreferencesKey.selectedCategoryId] = categoryId
            preferences[PreferencesKey.selectedTag] = tag
            preferences[PreferencesKey.selectedTagId] = tagId
            preferences[PreferencesKey.checkedControl] = checkedControl
        }
    }

    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.backOnline] = backOnline
        }
    }

    val readBrandAndCategory: Flow<BrandAndCategory> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedBrand = preferences[PreferencesKey.selectedBrand] ?: DEFAULT_BRAND
            val selectedBrandId = preferences[PreferencesKey.selectedBrandId] ?: 0
            val selectedCategory = preferences[PreferencesKey.selectedCategory] ?: DEFAULT_CATEGORY
            val selectedCategoryId = preferences[PreferencesKey.selectedCategoryId] ?: 0
            val tag = preferences[PreferencesKey.selectedTag] ?: DEFAULT_TAGS
            val tagId = preferences[PreferencesKey.selectedTagId] ?: 0
            val checkedControl = preferences[PreferencesKey.checkedControl] ?: false
            BrandAndCategory(
                selectedBrand,
                selectedBrandId,
                selectedCategory,
                selectedCategoryId,
                tag,
                tagId,
                checkedControl
            )
        }

    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[PreferencesKey.backOnline] ?: false
            backOnline
        }


}

data class BrandAndCategory(
    val selectedBrand: String,
    val selectedBrandId: Int,
    val selectedCategory: String,
    val selectedCategoryId: Int,
    val selectedTag: String,
    val selectedTagId: Int,
    val checkedControl: Boolean
)