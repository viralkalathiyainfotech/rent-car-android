package com.example.rentcar.ui.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rentcar.R
import com.example.rentcar.model.home.BrandResponseModel.BrandResponseModelItem

class BrandAdapter(
    private var list: List<BrandResponseModelItem>,
    private val onExploreClick: (BrandResponseModelItem) -> Unit
) : RecyclerView.Adapter<BrandAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val topAccent  : View     = view.findViewById(R.id.viewTopAccent)
        val tvInitials : TextView = view.findViewById(R.id.tvInitials)
        val tvCategory : TextView = view.findViewById(R.id.tvCategory)
        val tvRating   : TextView = view.findViewById(R.id.tvRating)
        val tvName     : TextView = view.findViewById(R.id.tvBrandName)
        val tvTagline  : TextView = view.findViewById(R.id.tvTagline)
        val tvCount    : TextView = view.findViewById(R.id.tvCarsCount)
        val btnExplore : TextView = view.findViewById(R.id.btnExplore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_brand_card, parent, false)
        return VH(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]

        // ── Fix: API sends "#F5A280" or "F5A280" — handle both ──
        val colorStr = if (item.accent.startsWith("#")) item.accent else "#${item.accent}"
        val color = try {
            Color.parseColor(colorStr)
        } catch (e: Exception) {
            Color.parseColor("#FFE07B2B")
        }

        // Top color bar
        holder.topAccent.setBackgroundColor(color)

        // Initials box background — same accent color with opacity
        val initialsDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(Color.argb(40, Color.red(color), Color.green(color), Color.blue(color)))
            setStroke(1, color)
            cornerRadius = 8f
        }
        holder.tvInitials.background = initialsDrawable
        holder.tvInitials.setTextColor(color)   // ← initials text also accent colored

        // Initials text
        holder.tvInitials.text = item.subtitle
        holder.tvInitials.isSelected = true

        // Category badge
        holder.tvCategory.text = item.tag.uppercase()
        holder.tvCategory.setTextColor(color)
        holder.tvCategory.background = buildBadge(color)

        // Rating
        holder.tvRating.text = "5.0"

        // Name & tagline
        holder.tvName.text    = item.name
        holder.tvTagline.text = item.tagline

        // Cars count
        holder.tvCount.text = "Cars Available"

        // Explore button
        holder.btnExplore.setTextColor(color)
        holder.btnExplore.setOnClickListener { onExploreClick(item) }
        holder.itemView.setOnClickListener { onExploreClick(item) }
    }

    fun updateList(newList: List<BrandResponseModelItem>) {
        list = newList
        notifyDataSetChanged()
    }

    private fun buildBadge(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(Color.TRANSPARENT)
            setStroke(2, color)
            cornerRadius = 4f
        }
    }
}