package com.example.rentcar.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rentcar.R
import com.example.rentcar.base.BaseVMFragment
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.databinding.FragmentGalleryBinding
import com.example.rentcar.model.home.CarResponseModel
import com.example.rentcar.ui.adapter.GalleryAdapter
import com.example.rentcar.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : BaseVMFragment<FragmentGalleryBinding, HomeViewModel>(
    FragmentGalleryBinding::inflate,
    HomeViewModel::class.java
) {
    private lateinit var galleryAdapter: GalleryAdapter
    private var allCars: List<CarResponseModel.CarResponseModelItem> = emptyList()
    private var selectedCategory = "All"
    private var dynamicCategories: List<String> = emptyList()

    override fun initViews() {
        setupRecyclerView()
        viewModel.getAllCars()
    }

    override fun initListeners() {}

    override fun initObservers() {
        viewModel.carsState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    allCars = result.data

                    dynamicCategories = buildDynamicCategories(allCars)
                    selectedCategory = "All"
                    setupCategoryTabs()
                    filterCars("All")
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showToast(result.message)
                }
            }
        }
    }

    private fun buildDynamicCategories(
        cars: List<CarResponseModel.CarResponseModelItem>
    ): List<String> {
        val uniqueCategories = cars
            .map { it.category.trim() }
            .filter { it.isNotBlank() }
            .distinct()
            .sorted()
        return listOf("All") + uniqueCategories
    }

    private fun setupRecyclerView() {
        galleryAdapter = GalleryAdapter(emptyList()) { car -> }
        binding.rvGallery.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvGallery.adapter = galleryAdapter
    }

    private fun setupCategoryTabs() {
        binding.tabLayout.removeAllViews()

        dynamicCategories.forEach { category ->
            val tab = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_category_tab, binding.tabLayout, false) as TextView

            tab.text = category
            updateTabStyle(tab, category == selectedCategory)

            tab.setOnClickListener {
                selectedCategory = category
                filterCars(category)
                updateAllTabs()
            }

            binding.tabLayout.addView(tab)
        }
    }

    private fun filterCars(category: String) {
        val filtered = if (category == "All") {
            allCars
        } else {
            allCars.filter {
                it.category.trim().equals(category, ignoreCase = true)
            }
        }

        galleryAdapter.updateList(filtered)
        binding.tvEmpty.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun updateAllTabs() {
        for (i in 0 until binding.tabLayout.childCount) {
            val tab = binding.tabLayout.getChildAt(i) as? TextView ?: continue
            updateTabStyle(tab, tab.text == selectedCategory)
        }
    }

    private fun updateTabStyle(tab: TextView, isSelected: Boolean) {
        if (isSelected) {
            tab.setBackgroundResource(R.drawable.bg_tab_selected)
            tab.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            tab.typeface = ResourcesCompat.getFont(requireContext(), R.font.general_sans_regular)

        } else {
            tab.setBackgroundResource(R.drawable.bg_tab_unselected)
            tab.setTextColor(ContextCompat.getColor(requireContext(), R.color.themeGrey))
            tab.typeface = ResourcesCompat.getFont(requireContext(), R.font.general_sans_regular)
        }
    }
}
