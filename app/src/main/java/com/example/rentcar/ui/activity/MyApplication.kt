
package com.example.rentcar.ui.activity

import android.app.Application
import com.example.rentcar.apiService.RetrofitClient
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        RetrofitClient.init()
    }
}