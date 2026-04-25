package com.example.rentcar.apiService

import android.util.Log
import com.app.pan.book.utils.SharedPrefManager
import com.example.rentcar.base.AppController
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = try {
            AppController.instance?.let {
                SharedPrefManager(it).token    // ← .token use karo
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



//class AuthInterceptor : Interceptor {
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val token = AppController.instance?.let { SharedPrefManager(it) }?.getString("token")
//        Log.d("AuthInterceptor", "Token from SharedPreferences: $token")
//
//        val requestBuilder = chain.request().newBuilder()
//        if (!token.isNullOrEmpty()) {
//            requestBuilder.addHeader("Authorization", "Bearer $token")
//        }
//        return chain.proceed(requestBuilder.build())
//    }
//}