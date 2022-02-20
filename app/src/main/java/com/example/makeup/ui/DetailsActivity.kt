package com.example.makeup.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.fooder.util.extensions.showSnackBar
import com.example.makeup.R
import com.example.makeup.adapters.PagerAdapter
import com.example.makeup.data.database.entities.FavoritesEntity
import com.example.makeup.databinding.ActivityDetailsBinding
import com.example.makeup.ui.fragments.colors.ColorsFragment
import com.example.makeup.ui.fragments.instructions.WebsiteFragment
import com.example.makeup.ui.fragments.overview.OverviewFragment
import com.example.makeup.util.Constants.Companion.COLORS_FRAGMENT
import com.example.makeup.util.Constants.Companion.WEBSITE_FRAGMENT
import com.example.makeup.util.Constants.Companion.OVERVIEW_FRAGMENT
import com.example.makeup.util.Constants.Companion.PRODUCTS_BUNDLE_KEY
import com.example.makeup.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private var productSaved = false
    private var savedProductId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolBar)
        binding.toolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        with(fragments) {
            add(OverviewFragment())
            add(ColorsFragment())
            add(WebsiteFragment())
        }

        val titles = ArrayList<String>()
        with(titles) {
            add(OVERVIEW_FRAGMENT)
            add(COLORS_FRAGMENT)
            add(WEBSITE_FRAGMENT)
        }

        val resultBundle = Bundle()
        resultBundle.putParcelable(PRODUCTS_BUNDLE_KEY, args.productsItem)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.viewPager2.apply {
            isUserInputEnabled = false
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorites_menu)
        checkSavedProducts(menuItem!!)
        return true
    }

    private fun checkSavedProducts(menuItem: MenuItem) {
        mainViewModel.readFavoriteProducts.observe(this) { favoritesEntity ->
            try {
                for (savedProduct in favoritesEntity) {
                    if (savedProduct.productsItem.productId == args.productsItem.productId) {
                        changeMenuItemColor(menuItem, R.color.red)
                        savedProductId = savedProduct.id
                        productSaved = true
                        break
                    } else {
                        changeMenuItemColor(menuItem, R.color.white)
                    }
                }
            } catch (e: Exception) {
                Log.e("DetailsActivity", e.message.toString())
            }

        }
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favorites_menu && !productSaved) {
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_to_favorites_menu && productSaved) {
            removeFromFavorites(item)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(savedProductId, args.productsItem)
        mainViewModel.deleteFavoriteProduct(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from Favorites.", "Okay", binding.detailsLayout)
        productSaved = false
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            0, args.productsItem
        )
        mainViewModel.insertFavoriteProducts(favoritesEntity)
        changeMenuItemColor(item, R.color.red)
        showSnackBar("Product saved.", "Okay", binding.detailsLayout)
        productSaved = true
    }
}