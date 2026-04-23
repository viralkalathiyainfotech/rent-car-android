package com.example.rentcar.base.base

// base/BaseMultiTypeAdapter.kt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseMultiTypeAdapter<T> :
    RecyclerView.Adapter<BaseMultiTypeAdapter<T>.MultiViewHolder>() {

    private var items: MutableList<T> = mutableListOf()

    var onItemClick: ((item: T, position: Int) -> Unit)? = null

    abstract fun getViewType(item: T): Int
    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): ViewBinding
    abstract fun bindData(binding: ViewBinding, item: T, position: Int, viewType: Int)
    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean
    abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean

    override fun getItemViewType(position: Int): Int = getViewType(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiViewHolder {
        val binding = createBinding(LayoutInflater.from(parent.context), parent, viewType)
        return MultiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MultiViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<T>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = items.size
            override fun getNewListSize() = newItems.size
            override fun areItemsTheSame(oldPos: Int, newPos: Int) =
                areItemsTheSame(items[oldPos], newItems[newPos])
            override fun areContentsTheSame(oldPos: Int, newPos: Int) =
                areContentsTheSame(items[oldPos], newItems[newPos])
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class MultiViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T, position: Int) {
            bindData(binding, item, position, itemViewType)

            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_ID.toInt()) {
                    onItemClick?.invoke(item, pos)
                }
            }
        }
    }
}