package com.example.rentcar.base.base

// base/BaseDiffAdapter.kt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseDiffAdapter<T, VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseDiffAdapter<T, VB>.DiffViewHolder>(diffCallback) {

    var onItemClick: ((item: T, position: Int) -> Unit)? = null
    var onItemLongClick: ((item: T, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffViewHolder {
        val binding = bindingInflater(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DiffViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiffViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    abstract fun bindData(binding: VB, item: T, position: Int)

    inner class DiffViewHolder(private val binding: VB) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T, position: Int) {
            bindData(binding, item, position)

            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_ID.toInt()) {
                    onItemClick?.invoke(item, pos)
                }
            }

            binding.root.setOnLongClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_ID.toInt()) {
                    onItemLongClick?.invoke(item, pos)
                }
                true
            }
        }    }
}