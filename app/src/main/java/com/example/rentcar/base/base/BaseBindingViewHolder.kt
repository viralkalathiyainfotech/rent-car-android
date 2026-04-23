package com.example.rentcar.base.base

// base/BaseBindingViewHolder.kt

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseBindingViewHolder<T, VB : ViewBinding>(
    protected val binding: VB
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: T, position: Int)

    fun bindWithClick(
        item: T,
        position: Int,
        onClick: ((T, Int) -> Unit)? = null,
        onLongClick: ((T, Int) -> Unit)? = null
    ) {
        bind(item, position)

        binding.root.setOnClickListener {
            onClick?.invoke(item, position)
        }

        binding.root.setOnLongClickListener {
            onLongClick?.invoke(item, position)
            true
        }
    }
}