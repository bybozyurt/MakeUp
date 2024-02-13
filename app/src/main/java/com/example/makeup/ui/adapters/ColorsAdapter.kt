package com.example.makeup.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ab.makeup.databinding.ColorsRowLayoutBinding
import com.example.makeup.data.models.ProductColor
import com.example.makeup.util.ProductsDiffUtil

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
        with(holder.binding) {
            with(colorList[position]) {
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