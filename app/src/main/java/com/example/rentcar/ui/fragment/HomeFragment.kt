package com.example.rentcar.ui.fragment

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.rentcar.R
import com.example.rentcar.base.BaseVMFragment
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.base.utils.onClick
import com.example.rentcar.databinding.FragmentHomeBinding
import com.example.rentcar.ui.activity.MainActivity
import com.example.rentcar.ui.adapter.BrandAdapter
import com.example.rentcar.ui.adapter.VehicleAdapter
import com.example.rentcar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseVMFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java
) {
    private lateinit var vehicleAdapter: VehicleAdapter
    private lateinit var brandAdapter: BrandAdapter

    override fun initViews() {
        setupBrandsRecyclerView()
        setupVehiclesRecyclerView()
        viewModel.getAllCars()
        viewModel.getAllBrands()
    }

    override fun initListeners() {
        setupArrowButtons()
        binding.btnExploreNow.onClick {
            (requireActivity() as MainActivity).navigateToTab(R.id.nav_explore)
        }

        binding.btnEcoPerformance.onClick {
            (requireActivity() as MainActivity).navigateToTab(R.id.nav_explore)
        }

        binding.btnViewAllFleet.onClick {
            (requireActivity() as MainActivity).navigateToTab(R.id.nav_explore)
        }

        binding.btnExploreGallery.onClick {
            (requireActivity() as MainActivity).navigateToTab(R.id.nav_gallery)
        }
    }

    override fun initObservers() {
        viewModel.carsState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is UiState.Loading -> {}

                is UiState.Success -> {
                    Log.d("vehicleAdapter---", "initObservers: === ${result.data}")
                    vehicleAdapter.updateList(result.data.takeLast(4))


                    val lastThree = result.data.takeLast(3)

                    lastThree.getOrNull(0)?.let { car ->
                        Glide.with(this)
                            .load(car.image)
                            .placeholder(com.example.rentcar.R.drawable.dummy_image)
                            .centerCrop()
                            .into(binding.ivCarImage)
                    }

                    lastThree.getOrNull(1)?.let { car ->
                        Glide.with(this)
                            .load(car.image)
                            .placeholder(com.example.rentcar.R.drawable.dummy_image)
                            .centerCrop()
                            .into(binding.ivCarImage2)
                    }

                    lastThree.getOrNull(2)?.let { car ->
                        Glide.with(this)
                            .load(car.image)
                            .placeholder(com.example.rentcar.R.drawable.dummy_image)
                            .centerCrop()
                            .into(binding.ivCarImage3)
                    }
                }

                is UiState.Error -> {
                    showToast(result.message)
                }
            }
        }

        viewModel.brandsState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is UiState.Loading -> {}

                is UiState.Success -> {
                    brandAdapter.updateList(result.data)
                }

                is UiState.Error -> {
                    showToast(result.message)
                }
            }
        }
    }

    private fun setupBrandsRecyclerView() {
        brandAdapter = BrandAdapter(
            list = emptyList(),
            onExploreClick = { brand -> }
        )
        binding.rvBrands.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBrands.adapter = brandAdapter
        binding.rvBrands.isNestedScrollingEnabled = false
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

    private fun setupArrowButtons() {
        val scrollAmount = 400
        binding.btnBrandNext.setOnClickListener {
            binding.rvBrands.smoothScrollBy(scrollAmount, 0)
        }
        binding.btnBrandPrev.setOnClickListener {
            binding.rvBrands.smoothScrollBy(-scrollAmount, 0)
        }
    }
}