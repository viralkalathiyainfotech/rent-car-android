package com.example.rentcar.base.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class SimpleAdapter<T, VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val bind: (VB, T, Int) -> Unit,
    private val areItemsTheSame: (T, T) -> Boolean = { a, b -> a == b },
    private val areContentsTheSame: (T, T) -> Boolean = { a, b -> a == b }
) : RecyclerView.Adapter<SimpleAdapter<T, VB>.ViewHolder>() {

    private val items: MutableList<T> = mutableListOf()

    var onItemClick: ((T, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = bindingInflater(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount() = items.size

    fun updateList(newItems: List<T>) {
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

    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun removeItem(position: Int) {
        if (position in items.indices) {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
        }
    }

    inner class ViewHolder(private val binding: VB) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T, position: Int) {
            bind(binding, item, position)
            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_ID.toInt()) {
                    onItemClick?.invoke(item, pos)
                }
            }
        }
    }
}