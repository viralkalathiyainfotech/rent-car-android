package com.example.rentcar.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rentcar.R
import com.example.rentcar.model.home.CarResponseModel.CarResponseModelItem

class VehicleAdapter(
    private var list: List<CarResponseModelItem>,
    private val onRentNow: (CarResponseModelItem) -> Unit,
    private val onDetails: (CarResponseModelItem) -> Unit
) : RecyclerView.Adapter<VehicleAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
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

        Glide.with(holder.itemView.context)
            .load(item.image)
            .placeholder(R.drawable.dummy_image)
            .into(holder.ivCarImage)

        holder.tvCategory.text = item.category
        holder.tvCarName.text  = item.name
        holder.tvPrice.text    = "$${item.pricePerDay}"
        holder.tvFuelType.text = item.specs.fuel
        holder.tvTransmission.text = item.specs.transmission
        holder.tvSeats.text = item.specs.seating
        holder.tvAcceleration.text = item.specs.acceleration

        holder.btnRentNow.setOnClickListener { onRentNow(item) }
        holder.btnDetails.setOnClickListener { onDetails(item) }
    }

    fun updateList(newList: List<CarResponseModelItem>) {
        list = newList
        Log.d("list---", "updateList: ---${list}")
        notifyDataSetChanged()
    }
}