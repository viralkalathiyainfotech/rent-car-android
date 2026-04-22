package com.app.pan.book.utils

// utils/Constants.kt

object Constants {

    // API
    const val BASE_URL = "https://your-api.com/api/"
    const val API_TIMEOUT = 30L

    // SharedPref
    const val PREF_NAME = "app_preferences"

    // Intent Keys
    const val EXTRA_USER_ID = "extra_user_id"
    const val EXTRA_USER_NAME = "extra_user_name"
    const val EXTRA_DATA = "extra_data"

    // Bundle Keys
    const val BUNDLE_ID = "bundle_id"
    const val BUNDLE_TYPE = "bundle_type"

    // Request Codes
    const val REQUEST_CAMERA = 100
    const val REQUEST_GALLERY = 101
    const val REQUEST_LOCATION = 102

    // Date Formats
    const val DATE_FORMAT = "dd/MM/yyyy"
    const val TIME_FORMAT = "hh:mm a"
    const val DATE_TIME_FORMAT = "dd/MM/yyyy hh:mm a"
    const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    // Pagination
    const val PAGE_SIZE = 20
    const val INITIAL_PAGE = 1
}