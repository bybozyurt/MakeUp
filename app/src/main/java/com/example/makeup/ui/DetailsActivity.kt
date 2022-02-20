package com.example.makeup.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.makeup.R
import com.example.makeup.adapters.PagerAdapter
import com.example.makeup.databinding.ActivityDetailsBinding
import com.example.makeup.ui.fragments.colors.ColorsFragment
import com.example.makeup.ui.fragments.instructions.InstructionsFragment
import com.example.makeup.ui.fragments.overview.OverviewFragment
import com.example.makeup.util.Constants.Companion.COLORS_FRAGMENT
import com.example.makeup.util.Constants.Companion.INSTRUCTIONS_FRAGMENT
import com.example.makeup.util.Constants.Companion.OVERVIEW_FRAGMENT
import com.example.makeup.util.Constants.Companion.PRODUCTS_BUNDLE_KEY
import com.example.makeup.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

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
            add(InstructionsFragment())
        }

        val titles = ArrayList<String>()
        with(titles) {
            add(OVERVIEW_FRAGMENT)
            add(COLORS_FRAGMENT)
            add(INSTRUCTIONS_FRAGMENT)
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

        TabLayoutMediator(binding.tabLayout, binding.viewPager2){ tab, position ->
            tab.text = titles[position]
        }.attach()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}