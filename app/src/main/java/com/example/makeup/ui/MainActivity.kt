package com.example.makeup.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ab.makeup.R
import com.ab.makeup.databinding.ActivityMainBinding
import com.example.makeup.ui.base.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {


    private lateinit var navController: NavController

    @LayoutRes
    override fun getContentViewLayoutResId() = R.layout.activity_main

    override fun populateUI(savedInstanceState: Bundle?) {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.productsFragment,
                R.id.favoriteProductsFragment,
            )
        )

        mBinding?.bottomNavigationView?.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}