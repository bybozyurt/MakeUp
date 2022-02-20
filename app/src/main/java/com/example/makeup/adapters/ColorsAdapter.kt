package com.example.makeup.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.makeup.R
import com.example.makeup.databinding.ColorsRowLayoutBinding
import com.example.makeup.models.ProductColor
import com.example.makeup.util.ProductsDiffUtil
import com.squareup.picasso.Picasso
import java.math.RoundingMode.valueOf

class ColorsAdapter : RecyclerView.Adapter<ColorsAdapter.MyViewHolder>() {

    private var colorList = emptyList<ProductColor>()


    class MyViewHolder(val binding: ColorsRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ColorsRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder.binding){
            with(colorList[position]){
                val hexValue = Color.parseColor(hexValue)
                colorsName.text = colourName.toString()
                colorsImageView.setBackgroundColor(hexValue)
            }
        }
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    fun setData(newColorList: List<ProductColor>) {
        val colorsDiffUtil =
            ProductsDiffUtil(colorList, newColorList)
        val diffUtilResult = DiffUtil.calculateDiff(colorsDiffUtil)
        colorList = newColorList
        diffUtilResult.dispatchUpdatesTo(this)
    }

}