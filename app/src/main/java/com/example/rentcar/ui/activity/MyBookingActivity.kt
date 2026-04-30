package com.example.rentcar.ui.activity

import android.view.View
import com.app.pan.book.utils.SharedPrefManager
import com.example.rentcar.base.BaseVMActivity
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.base.utils.dialog.BookingDetailDialog
import com.example.rentcar.base.utils.onClick
import com.example.rentcar.databinding.ActivityMyBookingBinding
import com.example.rentcar.ui.adapter.MyBookingAdapter
import com.example.rentcar.viewModel.MyBookingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyBookingActivity : BaseVMActivity<ActivityMyBookingBinding, MyBookingViewModel>(
    ActivityMyBookingBinding::inflate,
    MyBookingViewModel::class.java
) {

    private val adapter by lazy {
        MyBookingAdapter { booking ->
            BookingDetailDialog
                .newInstance(booking)
                .show(supportFragmentManager, BookingDetailDialog.TAG)
        }
    }

    override fun initViews() {

        binding.rvMyBooking.adapter = adapter


        val token = SharedPrefManager.getInstance(this).token ?: ""
        viewModel.getMyBookings(token)
    }

    override fun initListeners() {
        binding.btnBack.onClick {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun initObservers() {
        viewModel.bookingState.observe(this) { result ->
            when (result) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvMyBooking.visibility = View.GONE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvMyBooking.visibility = View.VISIBLE

                    if (result.data.isEmpty()) {
                        showToast("No bookings found")
                    } else {
                        adapter.setItems(result.data)
                    }
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvMyBooking.visibility = View.VISIBLE
                    showToast(result.message)
                }
            }
        }
    }
}