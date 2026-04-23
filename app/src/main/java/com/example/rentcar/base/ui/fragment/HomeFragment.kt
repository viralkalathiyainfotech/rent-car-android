package com.example.rentcar.base.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentcar.base.base.BaseFragment
import com.example.rentcar.base.model.BrandModel
import com.example.rentcar.base.ui.adapter.BrandAdapter
import com.example.rentcar.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun initViews() {
        setupBrandsRecyclerView()
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