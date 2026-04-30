package com.example.rentcar.base.utils.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import com.app.pan.book.utils.SharedPrefManager
import com.example.rentcar.base.BaseDialogFragment
import com.example.rentcar.base.utils.onClick
import com.example.rentcar.base.utils.startActivityWithFlags
import com.example.rentcar.databinding.DialogLogOutBinding
import com.example.rentcar.ui.auth.LoginActivity

class LogoutDialog(
    private val onLogoutConfirmed: () -> Unit
) : BaseDialogFragment<DialogLogOutBinding>(
    DialogLogOutBinding::inflate
) {

    companion object {
        const val TAG = "LogoutDialog"

        fun newInstance(onLogoutConfirmed: () -> Unit): LogoutDialog {
            return LogoutDialog(onLogoutConfirmed)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.setCancelable(false)
    }

    override fun initViews() {}

    override fun initListeners() {

        binding.btnClose.onClick {
            dismiss()
        }

        binding.btnNoStay.onClick {
            dismiss()
        }

        binding.btnLogOut.onClick {
            SharedPrefManager.getInstance(requireContext()).logout()

            requireContext().startActivityWithFlags<LoginActivity>(
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
            onLogoutConfirmed()
            dismiss()
        }
    }
}