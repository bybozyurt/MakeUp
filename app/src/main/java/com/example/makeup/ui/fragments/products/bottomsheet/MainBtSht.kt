package com.example.makeup.ui.fragments.products.bottomsheet

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.makeup.R
import com.example.makeup.adapters.MainPagerAdapter
import com.example.makeup.databinding.FragmentMainBtShtBinding
import com.example.makeup.util.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainBtSht : BottomSheetDialogFragment() {

    private var _binding: FragmentMainBtShtBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBtShtBinding.inflate(layoutInflater, container, false)

        val fragments = ArrayList<Fragment>()
        with(fragments) {
            add(ProductBottomSheet())
            add(TagsProductBottomSheet())
        }

        val titles = ArrayList<String>()
        with(titles) {
            add(Constants.BRAND_AND_CATEGORY)
            add(Constants.ONLY_TAGS)

        }

        val mainPagerAdapter = MainPagerAdapter(
            fragments,
            requireActivity()
        )

        binding.viewPager2.apply {
            isUserInputEnabled = false
            adapter = mainPagerAdapter
        }


        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()


        return binding.root
    }


}