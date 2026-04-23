package com.example.rentcar.base.ui.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rentcar.R
import com.example.rentcar.base.model.BrandModel

class BrandAdapter(
    private val list: List<BrandModel>,
    private val onExploreClick: (BrandModel) -> Unit
) : RecyclerView.Adapter<BrandAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val topAccent: View = view.findViewById(R.id.viewTopAccent)
        val tvInitials: TextView = view.findViewById(R.id.tvInitials)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
        val tvName: TextView = view.findViewById(R.id.tvBrandName)
        val tvTagline: TextView = view.findViewById(R.id.tvTagline)
        val tvCount: TextView = view.findViewById(R.id.tvCarsCount)
        val btnExplore: TextView = view.findViewById(R.id.btnExplore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_brand_card, parent, false)
        return VH(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]
        val color = Color.parseColor(item.accentColor)

        // Top color bar
        holder.topAccent.setBackgroundColor(color)

        // Initials
        holder.tvInitials.text = item.initials

        // Category badge — outlined border matching accent color
        holder.tvCategory.text = item.category
        holder.tvCategory.setTextColor(color)
        holder.tvCategory.background = buildBadge(color)

        // Rating
        holder.tvRating.text = String.format("%.1f", item.rating)

        // Name & tagline
        holder.tvName.text = item.brandName
        holder.tvTagline.text = item.tagline

        // Cars count
        holder.tvCount.text = "${item.carsCount} Cars Available"

        // EXPLORE button color
        holder.btnExplore.setTextColor(color)
        holder.btnExplore.setOnClickListener { onExploreClick(item) }

        // Full card click
        holder.itemView.setOnClickListener { onExploreClick(item) }
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