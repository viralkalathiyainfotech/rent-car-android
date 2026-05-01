package com.example.rentcar.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rentcar.R
import com.example.rentcar.base.BaseVMActivity
import com.example.rentcar.databinding.ActivityCarDetailsBinding
import com.example.rentcar.databinding.ActivityLoginBinding
import com.example.rentcar.databinding.ActivityLoginBinding.inflate
import com.example.rentcar.ui.auth.LoginViewModel
import com.example.rentcar.viewModel.HomeViewModel

class CarDetailsActivity : BaseVMActivity<ActivityCarDetailsBinding, HomeViewModel>(
    ActivityCarDetailsBinding::inflate,
    HomeViewModel::class.java
) {
    override fun initViews() {
    }

    override fun initListeners() {
    }

    override fun initObservers() {
    }
}