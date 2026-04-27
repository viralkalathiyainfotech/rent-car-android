package com.example.rentcar.ui.activity

import androidx.fragment.app.Fragment
import com.example.rentcar.R
import com.example.rentcar.base.BaseActivity
import com.example.rentcar.base.ui.fragment.ExploreFleetFragment
import com.example.rentcar.base.ui.fragment.GalleryFragment
import com.example.rentcar.databinding.ActivityMainBinding
import com.example.rentcar.ui.fragment.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    companion object {
        private const val TAG_HOME    = "TAG_HOME"
        private const val TAG_EXPLORE = "TAG_EXPLORE"
        private const val TAG_GALLERY = "TAG_GALLERY"
    }

    override fun initViews() {
        openFragment(HomeFragment(), TAG_HOME)
        binding.bottomNav.selectedItemId = R.id.nav_home
    }

    override fun initListeners() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> { openFragment(HomeFragment(),    TAG_HOME);    true }
                R.id.nav_explore     -> { openFragment(ExploreFleetFragment(), TAG_EXPLORE); true }
                R.id.nav_gallery   -> { openFragment(GalleryFragment(), TAG_GALLERY); true }
                else              -> false
            }
        }
    }

    override fun initObservers() {}

    private fun openFragment(fragment: Fragment, tag: String) {
        if (isFinishing || isDestroyed) return
        if (supportFragmentManager.isStateSaved) return

        val target = supportFragmentManager.findFragmentByTag(tag) ?: fragment

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, target, tag)
            .commitAllowingStateLoss()
    }
}