package com.example.rentcar.ui.adapter

import com.example.rentcar.R
import com.example.rentcar.base.BaseRecyclerAdapter
import com.example.rentcar.base.utils.loadImage
import com.example.rentcar.databinding.ItemMyBookingBinding
import com.example.rentcar.model.MyBookingResponseItem
import java.text.SimpleDateFormat
import java.util.Locale

class MyBookingAdapter(
    private val onViewDetails: (MyBookingResponseItem) -> Unit
) : BaseRecyclerAdapter<MyBookingResponseItem, ItemMyBookingBinding>(
    ItemMyBookingBinding::inflate
) {


    override fun areItemsTheSame(
        oldItem: MyBookingResponseItem,
        newItem: MyBookingResponseItem
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: MyBookingResponseItem,
        newItem: MyBookingResponseItem
    ): Boolean = oldItem == newItem

    override fun bindData(
        binding: ItemMyBookingBinding,
        item: MyBookingResponseItem,
        position: Int
    ) {

        binding.txtTitle.text = item.car.name


        binding.txtCarID.text = item.id.takeLast(6).uppercase()


        binding.txtPrice.text = "$${item.totalAmount}"


        binding.txtPickupDate.text = formatDate(item.pickupDate)
        binding.txtReturnDate.text = formatDate(item.returnDate)

        binding.imgCar.loadImage(item.car.image, R.drawable.splash_image)

        binding.btnViewDetails.setOnClickListener {
            onViewDetails(item)
        }
    }

    private fun formatDate(isoDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val date = inputFormat.parse(isoDate)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            isoDate
        }
    }
}