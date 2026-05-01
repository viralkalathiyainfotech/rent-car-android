package com.example.rentcar.ui.adapter

import com.example.rentcar.R
import com.example.rentcar.base.BaseRecyclerAdapter
import com.example.rentcar.base.utils.loadImage
import com.example.rentcar.databinding.ItemMyBookingBinding
import com.example.rentcar.model.MyBookingResponseItem
import java.text.SimpleDateFormat
import java.util.Locale

class MyBookingAdapter(
    private val onViewDetails: (MyBookingResponseItem) -> Unit,
    private val onRatingChanged: (item: MyBookingResponseItem, rating: Int) -> Unit
) : BaseRecyclerAdapter<MyBookingResponseItem, ItemMyBookingBinding>(
    ItemMyBookingBinding::inflate
) {

    // ── Track booking IDs that already have a rating ──────
    // Populated from API response (item.rating > 0)
    // OR after user submits a new rating
    private val ratedBookingIds = mutableSetOf<String>()

    // ── Called from Activity after successful rating API ──
    fun markAsRated(bookingId: String) {
        ratedBookingIds.add(bookingId)
        // Find and rebind only the affected item
        val position = getAllItems().indexOfFirst { it.id == bookingId }
        if (position != -1) notifyItemChanged(position)
    }

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


        binding.ratingBar.onRatingBarChangeListener = null
        binding.ratingBar.rating = item.rating.toFloat()


        val alreadyRated = item.rating > 0 || ratedBookingIds.contains(item.id)

        if (alreadyRated) {

            binding.ratingBar.setIsIndicator(true)
            binding.ratingBar.alpha = 0.6f

        } else {

            binding.ratingBar.setIsIndicator(false)
            binding.ratingBar.alpha = 1.0f

            binding.ratingBar.setOnRatingBarChangeListener { _, ratingValue, fromUser ->
                if (fromUser && ratingValue > 0) {

                    binding.ratingBar.setIsIndicator(true)
                    binding.ratingBar.alpha = 0.6f
                    onRatingChanged(item, ratingValue.toInt())
                }
            }
        }

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