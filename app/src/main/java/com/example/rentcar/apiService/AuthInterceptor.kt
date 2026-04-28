package com.example.rentcar.apiService

import android.util.Log
import com.app.pan.book.utils.SharedPrefManager
import com.example.rentcar.application.MyApplication
import com.example.rentcar.base.AppController
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = try {
            MyApplication.instance?.let {
                SharedPrefManager(it).token
            }
        } catch (e: Exception) {
            null
        }

        Log.d("AuthInterceptor", "Token: $token")

        val request = chain.request().newBuilder().apply {
            if (!token.isNullOrEmpty()) {
                addHeader("Authorization", "Bearer $token")
            }
        }.build()

        return chain.proceed(request)
    }
}
