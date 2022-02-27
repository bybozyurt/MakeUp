package com.example.makeup.ui.fragments.products.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.makeup.databinding.FragmentProductBottomSheetBinding
import com.example.makeup.util.Constants.Companion.DEFAULT_BRAND
import com.example.makeup.util.Constants.Companion.DEFAULT_CATEGORY
import com.example.makeup.util.Constants.Companion.DEFAULT_TAGS
import com.example.makeup.util.extensions.gone
import com.example.makeup.util.extensions.show
import com.example.makeup.viewmodels.ProductsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*


class ProductBottomSheet : BottomSheetDialogFragment() {


    private lateinit var productViewModel: ProductsViewModel

    private var _binding: FragmentProductBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var brandChip = DEFAULT_BRAND
    private var brandChipId = 0
    private var categoryChip = DEFAULT_CATEGORY
    private var categoryChipId = 0
    private var tagChip = DEFAULT_TAGS
    private var tagChipId = 0
    private var isCheckedControl = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ViewModelProvider(requireActivity()).get(ProductsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductBottomSheetBinding.inflate(inflater, container, false)
        readBrandAndCategoryObserve()
        brandChipGroupListener()
        categoryChipGroupListener()
        tagChipGroupListener()
        applyButtonListener()
        onlyTagsSwitch()




        return binding.root
    }

    private fun onlyTagsSwitch() {
        binding.onlyTagsSwitch.setOnCheckedChangeListener { _, isChecked ->
            isCheckedControl = isChecked
            if (isCheckedControl) {
                binding.categoryTxt.gone()
                binding.categoryChipGroup.gone()
                binding.brandTxt.gone()
                binding.brandChipGroup.gone()
                binding.tagsTxt.show()
                binding.tagsChipGroup.show()
            } else {
                binding.tagsChipGroup.gone()
                binding.tagsTxt.gone()
                binding.categoryTxt.show()
                binding.categoryChipGroup.show()
                binding.brandTxt.show()
                binding.brandChipGroup.show()

            }
        }
    }


    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                val targetView = chipGroup.findViewById<Chip>(chipId)
                targetView.isChecked = true
                chipGroup.requestChildFocus(targetView, targetView)
            } catch (e: Exception) {
                Log.e("ProductsBottomSheet", e.message.toString())
            }
        }
    }

    private fun readBrandAndCategoryObserve() {
        productViewModel.readBrandAndCategory.asLiveData().observe(viewLifecycleOwner) { value ->
            brandChip = value.selectedBrand
            categoryChip = value.selectedCategory
            tagChip = value.selectedTag
            binding.onlyTagsSwitch.isChecked = value.checkedControl
            updateChip(value.selectedBrandId, binding.brandChipGroup)
            updateChip(value.selectedCategoryId, binding.categoryChipGroup)
            updateChip(value.selectedTagId, binding.tagsChipGroup)

        }
    }

    private fun brandChipGroupListener() {
        binding.brandChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            brandChip = selectedMealType
            brandChipId = selectedChipId

        }
    }

    private fun tagChipGroupListener() {
        binding.tagsChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedTag = chip.text.toString().lowercase(Locale.ROOT)
            tagChip = selectedTag
            tagChipId = selectedChipId
        }
    }

    private fun categoryChipGroupListener() {
        binding.categoryChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedCategory = chip.text.toString().lowercase(Locale.ROOT)
            categoryChip = selectedCategory
            categoryChipId = selectedChipId
        }
    }

    private fun applyButtonListener() {
        binding.applyBtn.setOnClickListener {

            productViewModel.saveBrandAndCategoryTemp(
                brandChip,
                brandChipId,
                categoryChip,
                categoryChipId,
                tagChip,
                tagChipId,
                isCheckedControl
            )
            Log.e("şahin", brandChip + "--" + categoryChip)

            val action =
                ProductBottomSheetDirections.actionProductBottomSheetToProductsFragment(true)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}