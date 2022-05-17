package com.example.dungeonans.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.example.dungeonans.DataClass.SpinnerModel
import com.example.dungeonans.R
import com.example.dungeonans.databinding.ItemSpinnerLinkBinding

class SocialLinkSpinnerAdapter(
    context: Context,
    @LayoutRes private val resId: Int,
    private val values: MutableList<SpinnerModel>
) : ArrayAdapter<SpinnerModel>(context, resId, values) {

    override fun getCount() = values.size


    override fun getItem(position: Int) = values[position]

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val model = values[position]
        try {
            binding.imgSpinner.setImageResource(model.image)
            binding.imgSpinner.setColorFilter(ContextCompat.getColor(context, R.color.white))
            binding.txtName.text = model.name
            binding.txtName.setTextColor(ContextCompat.getColor(context, R.color.white))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val model = values[position]
        try {
            binding.imgSpinner.setImageResource(model.image)
            binding.txtName.text = model.name

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return binding.root
    }

}