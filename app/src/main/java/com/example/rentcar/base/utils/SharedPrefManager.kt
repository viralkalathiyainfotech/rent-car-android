package com.app.pan.book.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPrefManager(context: Context) {

    companion object {
        private const val PREF_NAME = "app_preferences"
        private const val KEY_TOKEN = "token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_PHONE = "user_phone"
        private const val KEY_USER_PROFILE_IMAGE = "user_profile_image"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_IS_FIRST_TIME = "is_first_time"
        private const val KEY_LANGUAGE = "language"
        private const val KEY_DARK_MODE = "dark_mode"

        @Volatile
        private var instance: SharedPrefManager? = null

        fun getInstance(context: Context): SharedPrefManager {
            return instance ?: synchronized(this) {
                instance ?: SharedPrefManager(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // ==================== TOKEN ====================
    var token: String?
        get() = prefs.getString(KEY_TOKEN, null)
        set(value) = prefs.edit { putString(KEY_TOKEN, value) }

    // ==================== USER ====================
    var userId: String?
        get() = prefs.getString(KEY_USER_ID, null)
        set(value) = prefs.edit { putString(KEY_USER_ID, value)}

    var userName: String?
        get() = prefs.getString(KEY_USER_NAME, null)
        set(value) = prefs.edit { putString(KEY_USER_NAME, value)}

    var userEmail: String?
        get() = prefs.getString(KEY_USER_EMAIL, null)
        set(value) = prefs.edit { putString(KEY_USER_EMAIL, value)}

    var userPhone: String?
        get() = prefs.getString(KEY_USER_PHONE, null)
        set(value) = prefs.edit { putString(KEY_USER_PHONE, value)}

    var userProfileImage: String?
        get() = prefs.getString(KEY_USER_PROFILE_IMAGE, null)
        set(value) = prefs.edit { putString(KEY_USER_PROFILE_IMAGE, value)}

    // ==================== FLAGS ====================
    var isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = prefs.edit { putBoolean(KEY_IS_LOGGED_IN, value)}

    var isFirstTime: Boolean
        get() = prefs.getBoolean(KEY_IS_FIRST_TIME, true)
        set(value) = prefs.edit { putBoolean(KEY_IS_FIRST_TIME, value) }

    var language: String
        get() = prefs.getString(KEY_LANGUAGE, "en") ?: "en"
        set(value) = prefs.edit { putString(KEY_LANGUAGE, value) }

    var isDarkMode: Boolean
        get() = prefs.getBoolean(KEY_DARK_MODE, false)
        set(value) = prefs.edit {putBoolean(KEY_DARK_MODE, value) }

    // ==================== GENERIC METHODS ====================
    fun putString(key: String, value: String) = prefs.edit {putString(key, value) }
    fun getString(key: String, default: String = ""): String = prefs.getString(key, default) ?: default

    fun putInt(key: String, value: Int) = prefs.edit {putInt(key, value)}
    fun getInt(key: String, default: Int = 0): Int = prefs.getInt(key, default)

    fun putBoolean(key: String, value: Boolean) = prefs.edit {putBoolean(key, value)}
    fun getBoolean(key: String, default: Boolean = false): Boolean = prefs.getBoolean(key, default)

    fun putLong(key: String, value: Long) = prefs.edit { putLong(key, value) }
    fun getLong(key: String, default: Long = 0L): Long = prefs.getLong(key, default)

    // ==================== CLEAR ====================
    fun clearAll() {
        prefs.edit { clear() }
    }

    fun logout() {
        token = null
        userId = null
        userName = null
        userEmail = null
        isLoggedIn = false
    }
}