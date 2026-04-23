package com.example.rentcar.base

import android.app.Application


class AppController : Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    companion object {
        val TAG: String = AppController::class.java.simpleName

        @get:Synchronized
        var instance: AppController? = null
            private set
    }
}
