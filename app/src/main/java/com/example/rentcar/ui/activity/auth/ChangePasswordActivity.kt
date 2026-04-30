package com.example.rentcar.ui.activity.auth

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.app.pan.book.utils.SharedPrefManager
import com.example.rentcar.R
import com.example.rentcar.base.BaseVMActivity
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.base.utils.onClick
import com.example.rentcar.databinding.ActivityChangePasswordBinding
import com.example.rentcar.viewModel.ChangePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity :
    BaseVMActivity<ActivityChangePasswordBinding, ChangePasswordViewModel>(
        ActivityChangePasswordBinding::inflate,
        ChangePasswordViewModel::class.java
    ) {

    private var isOldPasswordVisible = false
    private var isNewPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun initViews() {}

    override fun initListeners() {


        binding.btnBack.onClick {
            onBackPressedDispatcher.onBackPressed()
        }


        binding.icOldPassword.onClick {
            isOldPasswordVisible = !isOldPasswordVisible
            binding.editOldPassword.transformationMethod =
                if (isOldPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()
            binding.icOldPassword.setImageResource(
                if (isOldPasswordVisible) R.drawable.ic_eye_show else R.drawable.ic_eye_hide
            )
            binding.editOldPassword.setSelection(binding.editOldPassword.text?.length ?: 0)
        }


        binding.icNewPassword.onClick {
            isNewPasswordVisible = !isNewPasswordVisible
            binding.editNewPassword.transformationMethod =
                if (isNewPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()
            binding.icNewPassword.setImageResource(
                if (isNewPasswordVisible) R.drawable.ic_eye_show else R.drawable.ic_eye_hide
            )
            binding.editNewPassword.setSelection(binding.editNewPassword.text?.length ?: 0)
        }


        binding.icConfirmPassword.onClick {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            binding.editConfirmPassword.transformationMethod =
                if (isConfirmPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()
            binding.icConfirmPassword.setImageResource(
                if (isConfirmPasswordVisible) R.drawable.ic_eye_show else R.drawable.ic_eye_hide
            )
            binding.editConfirmPassword.setSelection(binding.editConfirmPassword.text?.length ?: 0)
        }


        binding.btnUpdate.onClick {
            val token = SharedPrefManager.getInstance(this).token ?: ""
            viewModel.changePassword(
                token = token,
                oldPassword = binding.editOldPassword.text.toString(),
                newPassword = binding.editNewPassword.text.toString(),
                confirmPassword = binding.editConfirmPassword.text.toString()
            )
        }
    }

    override fun initObservers() {
        viewModel.changePasswordState.observe(this) { result ->
            when (result) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.txtUpdateProfile.visibility = View.GONE
                    binding.btnUpdate.isEnabled = false
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtUpdateProfile.visibility = View.VISIBLE
                    binding.btnUpdate.isEnabled = true

                    showToast(result.data.message)
                    finish()
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtUpdateProfile.visibility = View.VISIBLE
                    binding.btnUpdate.isEnabled = true

                    showToast(result.message)
                }
            }
        }
    }
}