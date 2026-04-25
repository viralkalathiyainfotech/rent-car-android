package com.example.rentcar.application
import android.app.Application
import com.example.rentcar.apiService.RetrofitClient
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp

class MyApplication : Application() {
    companion object {
        var instance: MyApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        RetrofitClient.init()
    }
}