package com.example.rentcar.ui.activity
import com.example.rentcar.base.BaseActivity
import com.example.rentcar.base.utils.onClick
import com.example.rentcar.base.utils.startActivityNormal
import com.example.rentcar.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity :
    BaseActivity<ActivityForgotPasswordBinding>(ActivityForgotPasswordBinding::inflate) {


    override fun initViews() {

        binding.btnSignIn.onClick {
            startActivityNormal<LoginActivity>()
        }
    }

    override fun initListeners() {

    }

    override fun initObservers() {

    }

}