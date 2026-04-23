package com.example.rentcar.apiService

import android.util.Log
import com.app.pan.book.utils.SharedPrefManager
import com.example.rentcar.base.AppController
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = AppController.instance?.let { SharedPrefManager(it) }?.getString("token")
        Log.d("AuthInterceptor", "Token from SharedPreferences: $token")

        val requestBuilder = chain.request().newBuilder()
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(requestBuilder.build())
    }
}