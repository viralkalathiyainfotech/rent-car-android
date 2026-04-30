package com.example.rentcar.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rentcar.R
import com.example.rentcar.databinding.ItemGalleryCardBinding
import com.example.rentcar.model.home.CarResponseModel.CarResponseModelItem

class GalleryAdapter(
    private var list: List<CarResponseModelItem>,
    private val onClick: (CarResponseModelItem) -> Unit
) : RecyclerView.Adapter<GalleryAdapter.VH>() {

    inner class VH(val binding: ItemGalleryCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemGalleryCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]
        with(holder.binding) {
            Glide.with(root.context)
                .load(item.image)
                .placeholder(R.drawable.dummy_image)
                .centerCrop()
                .into(ivCarImage)

            tvCarName.text = item.name
            tvCarCategory.text = item.category

            root.setOnClickListener { onClick(item) }
        }
    }

    fun updateList(newList: List<CarResponseModelItem>) {
        list = newList
        notifyDataSetChanged()
    }
}