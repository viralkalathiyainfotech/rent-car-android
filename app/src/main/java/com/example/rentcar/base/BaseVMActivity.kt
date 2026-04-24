package com.example.rentcar.base

// base/BaseVMActivity.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseVMActivity<VB : ViewBinding, VM : ViewModel>(
    private val bindingInflater: (LayoutInflater) -> VB,
    private val viewModelClass: Class<VM>
) : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[viewModelClass]

        initViews()
        initListeners()
        initObservers()
    }

    abstract fun initViews()
    abstract fun initListeners()
    abstract fun initObservers()

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}