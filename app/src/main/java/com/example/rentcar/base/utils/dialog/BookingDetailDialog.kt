package com.example.rentcar.base.utils.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import com.app.pan.book.utils.SharedPrefManager
import com.example.rentcar.R
import com.example.rentcar.base.BaseBottomSheet
import com.example.rentcar.base.utils.loadImage
import com.example.rentcar.base.utils.onClick
import com.example.rentcar.databinding.DialogBookingDetailsBinding
import com.example.rentcar.model.MyBookingResponseItem
import java.text.SimpleDateFormat
import java.util.Locale

class BookingDetailDialog(
    private val booking: MyBookingResponseItem
) : BaseBottomSheet<DialogBookingDetailsBinding>(
    DialogBookingDetailsBinding::inflate
) {

    companion object {
        const val TAG = "BookingDetailDialog"

        fun newInstance(booking: MyBookingResponseItem): BookingDetailDialog {
            return BookingDetailDialog(booking)
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.setCancelable(true)
    }

    @SuppressLint("SetTextI18n")
    override fun initViews() {
        val car = booking.car
        val pref = SharedPrefManager.getInstance(requireContext())

        binding.imgCar.loadImage(car.image, R.drawable.splash_image)
        binding.txtCarName.text = car.name
        binding.txtCarID.text = booking.id.takeLast(6).uppercase()
        binding.txtCustomerName.text = pref.userName ?: "N/A"
        binding.txtPrice.text = "$${booking.totalAmount}"
        binding.txtPickupDate.text = formatDate(booking.pickupDate)
        binding.txtReturnDate.text = formatDate(booking.returnDate)
        binding.txtTransmission.text = car.specs.transmission
        binding.txtFuelType.text = car.specs.fuel
        binding.txtSeating.text = car.specs.seating

        setStatus(booking.status)
    }

    override fun initListeners() {
        binding.btnClose.onClick {
            dismiss()
        }
    }

    private fun setStatus(status: String) {
        val statusText = status.uppercase().trim()
        binding.txtStatus.text = statusText

        when (statusText) {
            "COMPLETED" -> {
                binding.txtStatus.setBackgroundResource(R.drawable.bg_status_completed)
                binding.txtStatus.setTextColor(
                    Color.parseColor("#6C7CFF")
                )
            }

            "APPROVED" -> {
                binding.txtStatus.setBackgroundResource(R.drawable.bg_status_approved)
                binding.txtStatus.setTextColor(
                    Color.parseColor("#008000")
                )
            }

            "PENDING" -> {
                binding.txtStatus.setBackgroundResource(R.drawable.bg_status_pending)
                binding.txtStatus.setTextColor(
                    Color.parseColor("#FF8C2A")
                )
            }

            "CANCELLED" -> {
                binding.txtStatus.setBackgroundResource(R.drawable.bg_status_pending)
                binding.txtStatus.setTextColor(
                    Color.parseColor("#FF4444")
                )
            }

            else -> {

                binding.txtStatus.setBackgroundResource(R.drawable.bg_status)
                binding.txtStatus.setTextColor(
                    Color.parseColor("#6C7CFF")
                )
            }
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