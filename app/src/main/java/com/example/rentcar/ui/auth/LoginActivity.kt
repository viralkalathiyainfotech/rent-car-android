package com.example.rentcar.ui.auth

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import com.app.pan.book.utils.SharedPrefManager
import com.example.rentcar.R
import com.example.rentcar.base.BaseVMActivity
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.base.utils.onClick
import com.example.rentcar.base.utils.startActivityNormal
import com.example.rentcar.base.utils.startActivityWithFlags
import com.example.rentcar.databinding.ActivityLoginBinding
import com.example.rentcar.ui.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint   // ← add this import

@AndroidEntryPoint
class LoginActivity : BaseVMActivity<ActivityLoginBinding, LoginViewModel>(
    ActivityLoginBinding::inflate,
    LoginViewModel::class.java
) {

    private var isPasswordVisible = false

    override fun initViews() {}

    override fun initListeners() {

        binding.icLoginBtn.onClick {
            val email    = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            viewModel.login(email, password)
        }

        binding.icPassword.onClick {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.editPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.icPassword.setImageResource(R.drawable.ic_eye_show)
            } else {
                binding.editPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.icPassword.setImageResource(R.drawable.ic_eye_hide)
            }
            binding.editPassword.setSelection(
                binding.editPassword.text?.length ?: 0
            )
        }

        binding.btnSignIn.onClick {
            startActivityNormal<CreateAccountActivity>()
        }

        binding.btnForgotPassword.onClick {
            startActivityNormal<ForgotPasswordActivity>()
        }
    }

    override fun initObservers() {

        viewModel.loginState.observe(this) { result ->
            when (result) {

                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.txtLogin.visibility    = View.GONE
                    binding.icLoginBtn.isEnabled   = false
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtLogin.visibility    = View.VISIBLE
                    binding.icLoginBtn.isEnabled   = true

                    val user = result.data
                    SharedPrefManager(this).token     = user.token
                    SharedPrefManager(this).userId    = user._id
                    SharedPrefManager(this).userEmail = user.email

                    showToast("Welcome, ${user.firstname}!")
                    Log.d("SignIn", "${user.token} , ${user._id} , ${user.email}")

                    startActivityWithFlags<MainActivity>(
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                    )
                    finish()
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtLogin.visibility    = View.VISIBLE
                    binding.icLoginBtn.isEnabled   = true

                    showToast(result.message)
                }
            }
        }
    }
}