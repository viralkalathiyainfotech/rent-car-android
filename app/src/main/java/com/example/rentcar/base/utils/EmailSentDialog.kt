package com.example.rentcar.base.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import com.example.rentcar.base.BaseDialogFragment
import com.example.rentcar.databinding.DialogEmailSentBinding

class EmailSentDialog(
    private val email: String,
    private val onBackToLogin: () -> Unit
) : BaseDialogFragment<DialogEmailSentBinding>(DialogEmailSentBinding::inflate) {

    companion object {
        const val TAG = "EmailSentDialog"

        fun newInstance(email: String, onBackToLogin: () -> Unit): EmailSentDialog {
            return EmailSentDialog(email, onBackToLogin)
        }
    }

    override fun onStart() {
        super.onStart()
        // Make dialog background transparent so your bg_login_card shape shows properly
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.setCancelable(false)
    }

    override fun initViews() {
        // Show the email the user entered
        binding.txtEmail.text = email
    }

    override fun initListeners() {
        binding.icLoginBtn.onClick {
            dismiss()
            onBackToLogin()
        }
    }
}