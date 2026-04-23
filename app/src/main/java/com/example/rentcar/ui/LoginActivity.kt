package com.example.rentcar.ui

import com.example.rentcar.base.BaseActivity
import com.example.rentcar.base.utils.onClick
import com.example.rentcar.base.utils.startActivityNormal
import com.example.rentcar.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun initViews() {
    }

    override fun initListeners() {

        binding.btnSignIn.onClick {
            startActivityNormal<CreateAccountActivity>()
        }

        binding.btnForgotPassword.onClick {
            startActivityNormal<ForgotPasswordActivity>()
        }
    }

    override fun initObservers() {

    }
}