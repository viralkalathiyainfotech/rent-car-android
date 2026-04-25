package com.example.rentcar.application
import android.app.Application
import com.example.rentcar.apiService.RetrofitClient
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        RetrofitClient.init()
    }
}