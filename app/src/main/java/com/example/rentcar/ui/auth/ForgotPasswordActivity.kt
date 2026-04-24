package com.example.rentcar.ui.auth

import android.view.View
import com.example.rentcar.base.BaseVMActivity
import com.example.rentcar.base.utils.EmailSentDialog
import com.example.rentcar.base.utils.NetworkResult
import com.example.rentcar.base.utils.onClick
import com.example.rentcar.base.utils.startActivityNormal
import com.example.rentcar.databinding.ActivityForgotPasswordBinding
import com.example.rentcar.viewModel.ForgotPasswordViewModel

class ForgotPasswordActivity :
    BaseVMActivity<ActivityForgotPasswordBinding, ForgotPasswordViewModel>(
        ActivityForgotPasswordBinding::inflate,
        ForgotPasswordViewModel::class.java
    ) {

    override fun initViews() {}

    override fun initListeners() {


        binding.btnSignIn.onClick {
            startActivityNormal<LoginActivity>()
            finish()
        }


        binding.icLoginBtn.onClick {
            val email = binding.editEmail.text.toString()
            viewModel.forgotPassword(email)
        }
    }

    override fun initObservers() {
        viewModel.forgotPasswordState.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.txtLogin.visibility = View.GONE
                    binding.icLoginBtn.isEnabled = false
                }

                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtLogin.visibility = View.VISIBLE
                    binding.icLoginBtn.isEnabled = true

                    // Show success dialog with the entered email
                    val enteredEmail = binding.editEmail.text.toString().trim()
                    EmailSentDialog.newInstance(
                        email = enteredEmail,
                        onBackToLogin = {
                            startActivityNormal<LoginActivity>()
                            finish()
                        }
                    ).show(supportFragmentManager, EmailSentDialog.TAG)
                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtLogin.visibility = View.VISIBLE
                    binding.icLoginBtn.isEnabled = true
                    showToast(result.message)
                }
            }
        }
    }
}