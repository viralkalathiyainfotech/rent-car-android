package com.example.rentcar.ui.fragment

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentcar.R
import com.example.rentcar.base.BaseFragment
import com.example.rentcar.model.home.BrandModel
import com.example.rentcar.ui.adapter.BrandAdapter
import com.example.rentcar.databinding.FragmentHomeBinding
import com.example.rentcar.model.home.VehicleModel
import com.example.rentcar.ui.adapter.VehicleAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun initViews() {
        setupBrandsRecyclerView()
        setupVehiclesRecyclerView()
    }

    override fun initListeners() {
        setupArrowButtons()
    }

    override fun initObservers() {}

    private fun setupBrandsRecyclerView() {
        val brands = listOf(
            BrandModel(
                initials = "LB",
                category = "SPORTS",
                brandName = "LAMBORGHINI",
                tagline = "Expect the Extreme",
                carsCount = 1,
                accentColor = "#FFE07B2B"
            ),
            BrandModel(
                initials    = "BE",
                category    = "LUXURY",
                brandName   = "BENTLEY",
                tagline     = "Power & Refinement",
                carsCount   = 1,
                accentColor = "#FF2ECC71"
            ),
            BrandModel(
                initials    = "PO",
                category    = "SPORTS",
                brandName   = "PORSCHE",
                tagline     = "Drive Your Dreams",
                carsCount   = 1,
                accentColor = "#FFE74C3C"
            ),
            BrandModel(
                initials    = "AM",
                category    = "SPORTS",
                brandName   = "ASTON MARTIN",
                tagline     = "Art of Performance",
                carsCount   = 1,
                accentColor = "#FFF1C40F"
            ),
            BrandModel(
                initials    = "BU",
                category    = "HYPER",
                brandName   = "BUGATTI",
                tagline     = "Perfection of Form",
                carsCount   = 1,
                accentColor = "#FF6C5CE7"
            )
        )

        binding.rvBrands.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBrands.adapter = BrandAdapter(brands) { _ -> }
        binding.rvBrands.isNestedScrollingEnabled = false
    }

    private fun setupVehiclesRecyclerView() {
        val vehicles = listOf(
            VehicleModel(
                carName      = "LAMBORGHINI HURACÁN EVO123",
                category     = "SUPERCAR",
                price        = "$900",
                fuelType     = "Petrol",
                transmission = "Automatic",
                seats        = "2 Seater",
                speed        = "2.9 sec",
                imageRes     = R.drawable.dummy_image
            ),
            VehicleModel(
                carName      = "ROLLS-ROYCE PHANTOM",
                category     = "LUXURY",
                price        = "$2000",
                fuelType     = "Petrol",
                transmission = "Automatic",
                seats        = "5",
                speed        = "2.9 s",
                imageRes     = R.drawable.dummy_image
            ),
            VehicleModel(
                carName      = "MERCEDES-BENZ S-CLASS",
                category     = "LUXURY",
                price        = "$900",
                fuelType     = "Petrol",
                transmission = "Automatic",
                seats        = "5",
                speed        = "4.5s",
                imageRes     = R.drawable.dummy_image
            ),
            VehicleModel(
                carName      = "BMW M4 COMPETITION",
                category     = "SPORTS",
                price        = "$750",
                fuelType     = "Petrol",
                transmission = "Automatic",
                seats        = "4",
                speed        = "3.8s",
                imageRes     = R.drawable.dummy_image
            ),
            VehicleModel(
                carName      = "AUDI Q7",
                category     = "SUV",
                price        = "$600",
                fuelType     = "Diesel",
                transmission = "Automatic",
                seats        = "7",
                speed        = "6.5s",
                imageRes     = R.drawable.dummy_image
            ),
            VehicleModel(
                carName      = "TESLA MODEL 3",
                category     = "SUPERCAR",
                price        = "$650",
                fuelType     = "Electric",
                transmission = "Automatic",
                seats        = "4",
                speed        = "3.1s",
                imageRes     = R.drawable.dummy_image
            )
        )

        binding.rvVehicles.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvVehicles.adapter = VehicleAdapter(
            list      = vehicles,
            onRentNow = { _ -> },
            onDetails = { _ ->  }
        )
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