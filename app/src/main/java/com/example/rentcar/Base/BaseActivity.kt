package com.example.musiccreater.Base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.musiccreater.Manager.LanguageManager
import com.example.musiccreater.R
import com.example.musiccreater.Receiver.NetworkManager
import java.lang.reflect.ParameterizedType


abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private var _binding: VB? = null
    private var loadingDialog: Dialog? = null
    val binding: VB
        get() = _binding
            ?: throw kotlin.IllegalStateException("Binding is only valid between onCreate and onDestroy")

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageManager.applyLanguage(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NetworkManager.registerNetworkCallback(this)

        val superclass = javaClass.genericSuperclass
        val clazz = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        val method = clazz.getMethod(
            "inflate", LayoutInflater::class.java
        )
        @Suppress("UNCHECKED_CAST") _binding = method.invoke(null, layoutInflater) as VB
        setContentView(binding.root)
        init()
    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    abstract fun init()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
    fun showLoading(show: Boolean) {
        if (show) {
            if (loadingDialog == null) {
                loadingDialog = Dialog(this).apply {
                    setContentView(R.layout.dialog_loading)
                    setCancelable(false)
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            }
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
        }
    }
}
