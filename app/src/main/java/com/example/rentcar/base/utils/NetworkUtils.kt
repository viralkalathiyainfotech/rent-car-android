package com.app.pan.book.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object NetworkUtils {

    // ==================== CHECK INTERNET AVAILABLE ====================
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
            else -> false
        }
    }

    // ==================== CHECK WIFI CONNECTED ====================
    fun isWifiConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    // ==================== CHECK MOBILE DATA CONNECTED ====================
    fun isMobileDataConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    // ==================== GET NETWORK TYPE ====================
    fun getNetworkType(context: Context): NetworkType {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return NetworkType.NONE
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return NetworkType.NONE

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.WIFI
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.CELLULAR
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkType.ETHERNET
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> NetworkType.BLUETOOTH
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> NetworkType.VPN
            else -> NetworkType.NONE
        }
    }

    // ==================== GET NETWORK SPEED ====================
    fun getNetworkSpeed(context: Context): NetworkSpeed {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return NetworkSpeed.NONE
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return NetworkSpeed.NONE

        val downSpeed = capabilities.linkDownstreamBandwidthKbps
        val upSpeed = capabilities.linkUpstreamBandwidthKbps

        return NetworkSpeed(
            downloadKbps = downSpeed,
            uploadKbps = upSpeed,
            downloadMbps = downSpeed / 1000f,
            uploadMbps = upSpeed / 1000f
        )
    }

    // ==================== PING CHECK (Real Internet) ====================
    suspend fun hasRealInternet(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://clients3.google.com/generate_204")
                val connection = url.openConnection() as HttpURLConnection
                connection.setRequestProperty("User-Agent", "Android")
                connection.setRequestProperty("Connection", "close")
                connection.connectTimeout = 3000
                connection.readTimeout = 3000
                connection.connect()
                connection.responseCode == 204 && connection.contentLength == 0
            } catch (e: IOException) {
                false
            }
        }
    }

    // ==================== PING WITH CUSTOM URL ====================
    suspend fun pingUrl(url: String, timeoutMs: Int = 5000): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.connectTimeout = timeoutMs
                connection.readTimeout = timeoutMs
                connection.requestMethod = "HEAD"
                connection.connect()
                connection.responseCode in 200..399
            } catch (e: Exception) {
                false
            }
        }
    }

    // ==================== ENUMS & DATA CLASSES ====================
    enum class NetworkType {
        WIFI,
        CELLULAR,
        ETHERNET,
        BLUETOOTH,
        VPN,
        NONE
    }

    data class NetworkSpeed(
        val downloadKbps: Int = 0,
        val uploadKbps: Int = 0,
        val downloadMbps: Float = 0f,
        val uploadMbps: Float = 0f
    ) {
        companion object {
            val NONE = NetworkSpeed()
        }

        override fun toString(): String {
            return "Download: ${downloadMbps}Mbps, Upload: ${uploadMbps}Mbps"
        }
    }
}