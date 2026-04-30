package com.example.rentcar.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rentcar.R
import com.example.rentcar.base.BaseVMFragment
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.databinding.FragmentExploreBinding
import com.example.rentcar.model.home.CarResponseModel
import com.example.rentcar.ui.adapter.VehicleAdapter
import com.example.rentcar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseVMFragment<FragmentExploreBinding, HomeViewModel>(
    FragmentExploreBinding::inflate,
    HomeViewModel::class.java
) {
    private lateinit var vehicleAdapter: VehicleAdapter
    private var allCars: List<CarResponseModel.CarResponseModelItem> = emptyList()
    private var isLowToHigh = true  //  Default: Low to High

    override fun initViews() {
        setupVehiclesRecyclerView()
        viewModel.getAllCars()
    }

    override fun initListeners() {
        binding.tvSortOption.setOnClickListener {
            showSortPopup()
        }
        binding.ivFilterIcon.setOnClickListener {
            showSortPopup()
        }
    }

    private fun showSortPopup() {
        val wrapper = android.view.ContextThemeWrapper(requireContext(), R.style.DarkPopupMenu)
        val popupMenu = android.widget.PopupMenu(wrapper, binding.tvSortOption)

        popupMenu.menu.add(0, 0, 0, "Price: Low to High")
        popupMenu.menu.add(0, 1, 1, "Price: High to Low")

        popupMenu.menu.getItem(if (isLowToHigh) 0 else 1).isChecked = true

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                0 -> {
                    isLowToHigh = true
                    binding.tvSortOption.text = "Price: Low to High"
                }
                1 -> {
                    isLowToHigh = false
                    binding.tvSortOption.text = "Price: High to Low"
                }
            }
            applySorting()
            true
        }
        popupMenu.show()
    }

    override fun initObservers() {
        viewModel.carsState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    allCars = result.data
                    applySorting()
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showToast(result.message)
                }
            }
        }
    }

    private fun applySorting() {
        val sorted = if (isLowToHigh) {
            binding.tvSortOption.text = "Price: Low to High"
            allCars.sortedBy { it.pricePerDay }
        } else {
            binding.tvSortOption.text = "Price: High to Low"
            allCars.sortedByDescending { it.pricePerDay }
        }

        vehicleAdapter.updateList(sorted)
        binding.tvEmpty.visibility = if (sorted.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun setupVehiclesRecyclerView() {
        vehicleAdapter = VehicleAdapter(
            list = emptyList(),
            onRentNow = { car -> },
            onDetails = { car -> }
        )
        binding.rvVehicles.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvVehicles.adapter = vehicleAdapter
        binding.rvVehicles.isNestedScrollingEnabled = false
    }
}