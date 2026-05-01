package com.example.rentcar.ui.activity

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.app.pan.book.utils.SharedPrefManager
import com.example.rentcar.base.BaseActivity
import com.example.rentcar.databinding.ActivitySplashActviityBinding
import com.example.rentcar.ui.auth.LoginActivity

class SplashActivity :
    BaseActivity<ActivitySplashActviityBinding>(ActivitySplashActviityBinding::inflate) {

    private val handler = Handler(Looper.getMainLooper())

    private val splashRunnable = Runnable {
        navigateToNextScreen()
    }

    override fun initViews() {
        handler.postDelayed(splashRunnable, 3500)
    }

    override fun initListeners() {}

    override fun initObservers() {}

    private fun navigateToNextScreen() {
        val pref = SharedPrefManager.getInstance(this)

        val intent = when {
            pref.isLoggedIn -> {
                Intent(this, MainActivity::class.java)
            }
            else -> {
                Intent(this, LoginActivity::class.java)
            }
        }
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        handler.removeCallbacks(splashRunnable)
    }
}