package com.example.rentcar.ui.auth

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import com.example.rentcar.R
import com.example.rentcar.base.BaseVMActivity
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.base.utils.onClick
import com.example.rentcar.base.utils.startActivityNormal
import com.example.rentcar.databinding.ActivityCreateAccountBinding
import com.example.rentcar.ui.activity.MainActivity
import com.example.rentcar.viewModel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountActivity :
    BaseVMActivity<ActivityCreateAccountBinding, RegisterViewModel>(
        ActivityCreateAccountBinding::inflate,
        RegisterViewModel::class.java
    ) {

    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun initViews() {}

    override fun initListeners() {

        // ── Sign in redirect
        binding.btnSignIn.onClick {
            startActivityNormal<MainActivity>()
        }

        // ── Toggle password visibility
        binding.icPassword.onClick {
            isPasswordVisible = !isPasswordVisible
            binding.editPassword.transformationMethod =
                if (isPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()
            binding.icPassword.setImageResource(
                if (isPasswordVisible) R.drawable.ic_eye_show else R.drawable.ic_eye_hide
            )
            binding.editPassword.setSelection(binding.editPassword.text?.length ?: 0)
        }

        binding.icConfirmPassword.onClick {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            binding.editConfimrPassword.transformationMethod =
                if (isConfirmPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()
            binding.icConfirmPassword.setImageResource(
                if (isConfirmPasswordVisible) R.drawable.ic_eye_show else R.drawable.ic_eye_hide
            )
            binding.editConfimrPassword.setSelection(
                binding.editConfimrPassword.text?.length ?: 0
            )
        }

        // ── Create account button
        binding.icLoginBtn.onClick {
            if (!binding.checkBox.isChecked) {
                showToast("Please accept the Terms of Service and Privacy Policy")
                return@onClick
            }
            viewModel.register(
                firstname = binding.editUsername.text.toString(),
                lastname = binding.editLatName.text.toString(),
                email = binding.editEmail.text.toString(),
                phone = binding.editPhoneNumber.text.toString(),
                licenceNo = binding.editDrivingLicense.text.toString(),
                password = binding.editPassword.text.toString(),
                confirmPassword = binding.editConfimrPassword.text.toString()
            )
        }

        // ── Optional links ───────
        binding.textTerms.onClick {
            showToast("Terms of Service")
        }
        binding.textPrivacy.onClick {
            showToast("Privacy Policy")
        }
    }

    override fun initObservers() {
        viewModel.registerState.observe(this) { result ->
            when (result) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.icRegisterBtn.visibility = View.GONE
                    binding.icLoginBtn.isEnabled = false
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.icRegisterBtn.visibility = View.VISIBLE
                    binding.icLoginBtn.isEnabled = true
                    Log.e("LoginSuccess", "Error: ${result.data}")

                    showToast("Account created successfully!")
                    startActivityNormal<LoginActivity>()
                    finish()
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.icRegisterBtn.visibility = View.VISIBLE
                    binding.icLoginBtn.isEnabled = true
                    Log.e("LoginError", "Error: ${result.message}")

                    showToast(result.message)
                }
            }
        }
    }
}