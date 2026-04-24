package com.example.rentcar.ui.auth

import com.example.rentcar.base.BaseActivity
import com.example.rentcar.base.utils.onClick
import com.example.rentcar.base.utils.startActivityNormal
import com.example.rentcar.databinding.ActivityCreateAccountBinding

class CreateAccountActivity :
    BaseActivity<ActivityCreateAccountBinding>(ActivityCreateAccountBinding::inflate) {

    override fun initViews() {

    }

    override fun initListeners() {

        binding.btnSignIn.onClick {
            startActivityNormal<LoginActivity>()
        }
    }

    override fun initObservers() {

    }
}