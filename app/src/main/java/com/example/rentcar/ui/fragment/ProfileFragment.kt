package com.example.rentcar.ui.fragment

import android.content.Intent
import com.app.pan.book.utils.SharedPrefManager
import com.example.rentcar.base.BaseFragment
import com.example.rentcar.base.utils.dialog.LogoutDialog
import com.example.rentcar.base.utils.loadImage
import com.example.rentcar.base.utils.onClick
import com.example.rentcar.base.utils.startActivityNormal
import com.example.rentcar.base.utils.startActivityWithFlags
import com.example.rentcar.databinding.FragmentProfileBinding
import com.example.rentcar.ui.activity.MyBookingActivity
import com.example.rentcar.ui.activity.auth.ChangePasswordActivity
import com.example.rentcar.ui.activity.profile.EditProfileActivity
import com.example.rentcar.ui.auth.LoginActivity

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun initViews() {
    }

    override fun initListeners() {

        binding.btnLogOut.onClick {
            logout()
        }

        binding.btnEditProfile.onClick {
            requireContext().startActivityNormal<EditProfileActivity>()
        }

        binding.btnMyBooking.onClick {
            requireContext().startActivityNormal<MyBookingActivity>()
        }

        binding.btnChangePass.onClick {
            requireContext().startActivityNormal<ChangePasswordActivity>()
        }
    }

    override fun initObservers() {}

    private fun logout() {
        LogoutDialog.newInstance(
            onLogoutConfirmed = { logout() }
        ).show(childFragmentManager, LogoutDialog.TAG)
    }

    override fun onResume() {
        super.onResume()
        val pref = SharedPrefManager.getInstance(requireContext())
        binding.txtProfileName.text = pref.userName ?: "N/A"
        binding.txtEmail.text = pref.userEmail ?: "N/A"
        binding.imgFolder.loadImage(pref.userProfileImage , isCircle = true)
    }
}