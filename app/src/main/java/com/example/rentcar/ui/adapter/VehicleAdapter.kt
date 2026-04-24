package com.example.rentcar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rentcar.R
import com.example.rentcar.model.home.VehicleModel

class VehicleAdapter(
    private val list: List<VehicleModel>,
    private val onRentNow: (VehicleModel) -> Unit,
    private val onDetails: (VehicleModel) -> Unit
) : RecyclerView.Adapter<VehicleAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        // Exact IDs from item_vehicle_card.xml
        val ivCarImage: ImageView    = view.findViewById(R.id.ivCarImage)
        val tvCategory: TextView     = view.findViewById(R.id.tvCategory)
        val tvCarName: TextView      = view.findViewById(R.id.tvCarName)
        val tvPrice: TextView        = view.findViewById(R.id.tvPrice)
        val tvFuelType: TextView     = view.findViewById(R.id.tvFuelType)
        val tvTransmission: TextView = view.findViewById(R.id.tvTransmission)
        val tvSeats: TextView        = view.findViewById(R.id.tvSeats)
        val tvAcceleration: TextView = view.findViewById(R.id.tvAcceleration)
        val btnRentNow: TextView     = view.findViewById(R.id.btnRentNow)
        val btnDetails: TextView     = view.findViewById(R.id.btnDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicle_card, parent, false)
        return VH(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]

        holder.ivCarImage.setImageResource(item.imageRes)
        holder.tvCategory.text     = item.category
        holder.tvCarName.text      = item.carName
        holder.tvPrice.text        = item.price
        holder.tvFuelType.text     = item.fuelType
        holder.tvTransmission.text = item.transmission
        holder.tvSeats.text        = item.seats
        holder.tvAcceleration.text = item.speed

        holder.btnRentNow.setOnClickListener { onRentNow(item) }
        holder.btnDetails.setOnClickListener { onDetails(item) }
    }
}