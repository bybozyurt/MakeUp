package com.example.makeup.ui.fragments.products.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.makeup.databinding.FragmentTagsProductBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TagsProductBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentTagsProductBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTagsProductBottomSheetBinding.inflate(inflater, container, false)



        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}