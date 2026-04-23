package com.example.rentcar.base.base

// base/BaseRecyclerAdapter.kt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerAdapter<T, VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB
) : RecyclerView.Adapter<BaseRecyclerAdapter<T, VB>.ViewHolder>() {

    private var items: MutableList<T> = mutableListOf()

    var onItemClick: ((item: T, position: Int) -> Unit)? = null
    var onItemLongClick: ((item: T, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = bindingInflater(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = items.size

    abstract fun bindData(binding: VB, item: T, position: Int)

    open fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
    open fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem


    // ==================== ViewHolder ====================
    inner class ViewHolder(private val binding: VB) :
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
        }
    }

    // ==================== LIST OPERATIONS ====================
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

    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(newItems: List<T>) {
        val startPos = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(startPos, newItems.size)
    }

    fun addItemAt(position: Int, item: T) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        if (position in items.indices) {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
        }
    }

    fun removeItem(item: T) {
        val position = items.indexOf(item)
        if (position != -1) {
            removeItem(position)
        }
    }

    fun updateItem(position: Int, item: T) {
        if (position in items.indices) {
            items[position] = item
            notifyItemChanged(position)
        }
    }

    fun clearItems() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun getItem(position: Int): T = items[position]

    fun getAllItems(): List<T> = items.toList()

    fun isEmpty(): Boolean = items.isEmpty()

    fun getItemsCount(): Int = items.size

    fun swapItems(fromPos: Int, toPos: Int) {
        val temp = items[fromPos]
        items[fromPos] = items[toPos]
        items[toPos] = temp
        notifyItemMoved(fromPos, toPos)
    }
}