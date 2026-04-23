package com.example.rentcar.base.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.rentcar.R
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ==================== CONTEXT ====================
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

// ==================== ACTIVITY ====================
inline fun <reified T : Activity> Context.startActivity(extras: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    extras?.let { intent.putExtras(it) }
    startActivity(intent)
}

inline fun <reified T : Activity> Context.startActivityClearTask() {
    val intent = Intent(this, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
}

inline fun <reified T : Activity> Context.startActivityNormal() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

inline fun <reified T : Activity> Context.startActivityWithFlags(
    flags: Int
) {
    val intent = Intent(this, T::class.java).apply {
        this.flags = flags
    }
    startActivity(intent)
}

inline fun <reified T : Activity> Activity.launchActivityForResult(
    launcher: ActivityResultLauncher<Intent>
) {
    val intent = Intent(this, T::class.java)
    launcher.launch(intent)
}

inline fun <reified T : Activity> Fragment.startActivitySafe() {
    val intent = Intent(requireContext(), T::class.java)
    startActivity(intent)
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

// ==================== VIEW ====================
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.toggleVisibility() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun View.showSnackbarWithAction(
    message: String,
    actionText: String,
    action: () -> Unit
) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .setAction(actionText) { action() }
        .show()
}

fun View.enable() {
    isEnabled = true; alpha = 1.0f
}

fun View.disable() {
    isEnabled = false; alpha = 0.5f
}

// ==================== EDITTEXT ====================
fun EditText.onTextChanged(callback: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            callback(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}

fun EditText.trimmedText(): String = text.toString().trim()

// ==================== IMAGEVIEW ====================
fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .into(this)
}

fun ImageView.loadImage(
    url: String?,
    @DrawableRes placeholder: Int = R.drawable.place_holder,
    @DrawableRes error: Int = R.drawable.place_holder
) {
    Glide.with(this.context)
        .load(url)
        .placeholder(placeholder)   // while loading
        .error(error)               // if failed
        .fallback(placeholder)      // if url = null
        .centerCrop()
        .into(this)
}

fun ImageView.loadImage(
    url: String?,
    @DrawableRes placeholder: Int = R.drawable.place_holder,
    @DrawableRes error: Int = R.drawable.place_holder,
    isCircle: Boolean = false
) {
    var request = Glide.with(this.context)
        .load(url)
        .placeholder(placeholder)
        .error(error)
        .fallback(placeholder)

    request = if (isCircle) {
        request.circleCrop()
    } else {
        request.centerCrop()
    }

    request.into(this)
}


fun ImageView.loadImageWithCallback(
    url: String?,
    onSuccess: () -> Unit = {},
    onError: () -> Unit = {}
) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.place_holder)
        .error(R.drawable.place_holder)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onError()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onSuccess()
                return false
            }
        })
        .into(this)
}


fun ImageView.loadImageCircle(url: String?) {
    Glide.with(this.context)
        .load(url)
        .circleCrop()
        .into(this)
}

// ==================== STRING ====================
fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPhone(): Boolean {
    return Patterns.PHONE.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.length >= 6
}

fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}

// ==================== DATE ====================
fun Long.toFormattedDate(pattern: String = "dd MMM yyyy"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(this))
}

fun Long.toFormattedDateTime(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(Date(this))
}

// ==================== DIALOG ====================
fun Context.showAlertDialog(
    title: String,
    message: String,
    positiveText: String = "OK",
    negativeText: String = "Cancel",
    onPositive: () -> Unit = {},
    onNegative: () -> Unit = {}
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveText) { _, _ -> onPositive() }
        .setNegativeButton(negativeText) { dialog, _ ->
            onNegative()
            dialog.dismiss()
        }
        .show()
}

// ==================== FRAGMENT ====================
fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.hideKeyboard() {
    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(requireView().windowToken, 0)
}

// ==================== VIEW CLICK ====================
inline fun View.onClick(crossinline action: () -> Unit) {
    setOnClickListener { action() }
}

inline fun View.onClickWithDebounce(debounceTime: Long = 600L, crossinline action: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime = 0L
        override fun onClick(v: View) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > debounceTime) {
                lastClickTime = currentTime
                action()
            }
        }
    })
}