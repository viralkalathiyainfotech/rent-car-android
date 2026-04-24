package com.example.rentcar.base

// base/BaseAdapter.kt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB
) : ListAdapter<T, BaseAdapter<T, VB>.BaseViewHolder>(diffCallback) {

    var onItemClick: ((T, Int) -> Unit)? = null
    var onItemLongClick: ((T, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = bindingInflater(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    abstract fun bindView(binding: VB, item: T, position: Int)

    inner class BaseViewHolder(private val binding: VB) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T, position: Int) {
            bindView(binding, item, position)

            binding.root.setOnClickListener {
                onItemClick?.invoke(item, position)
            }

            binding.root.setOnLongClickListener {
                onItemLongClick?.invoke(item, position)
                true
            }
        }
    }
}