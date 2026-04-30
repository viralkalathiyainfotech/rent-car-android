package com.example.rentcar.ui.activity.profile

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.app.pan.book.utils.SharedPrefManager
import com.bumptech.glide.Glide
import com.example.rentcar.R
import com.example.rentcar.base.BaseVMActivity
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.base.utils.loadImage
import com.example.rentcar.base.utils.onClick
import com.example.rentcar.databinding.ActivityEditProfileBinding
import com.example.rentcar.viewModel.EditProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : BaseVMActivity<ActivityEditProfileBinding, EditProfileViewModel>(
    ActivityEditProfileBinding::inflate,
    EditProfileViewModel::class.java
) {
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri? = result.data?.data
                uri?.let {
                    viewModel.setSelectedImage(it)

                    binding.ivProfile.loadImage(it.toString(), isCircle = true)
                }
            }
        }

    override fun initViews() {
        val pref = SharedPrefManager.getInstance(this)
        val fullName = pref.userName
            ?.split(" ")
            ?.map { it.trim() }
            ?.filter { it.isNotEmpty() }
            ?: emptyList()

        binding.etFirstName.setText(fullName.getOrNull(0) ?: "")
        binding.etLastName.setText(fullName.getOrNull(1) ?: "")
        binding.etEmail.setText(pref.userEmail?.trim() ?: "")
        binding.etMobile.setText(pref.userPhone?.trim() ?: "")
        binding.etLocation.setText(pref.userLocation?.trim() ?: "")

        val savedImg = pref.getString("user_profile_image", "")
        if (savedImg.isNotBlank()) {
            binding.ivProfile.loadImage(savedImg, isCircle = true)
        }
    }

    override fun initListeners() {

        binding.btnBack.onClick {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnCamera.onClick {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            imagePickerLauncher.launch(intent)
        }

        binding.btnUpdate.onClick {
            val pref = SharedPrefManager.getInstance(this)
            val token = pref.token ?: ""

            viewModel.updateProfile(
                context = this,
                token = token,
                firstName = binding.etFirstName.text.toString(),
                lastName = binding.etLastName.text.toString(),
                phone = binding.etMobile.text.toString(),
                location = binding.etLocation.text.toString(),
                imageUri = viewModel.selectedImageUri.value
            )
            Log.d("imgUrl---", "initListeners: ----${viewModel.selectedImageUri.value}")
        }
    }

    override fun initObservers() {
        viewModel.updateState.observe(this) { result ->
            when (result) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.txtUpdateProfile.visibility = View.GONE
                    binding.btnUpdate.isEnabled = false
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtUpdateProfile.visibility = View.VISIBLE
                    binding.btnUpdate.isEnabled = true

                    val user = result.data
                    val pref = SharedPrefManager.getInstance(this)

                    pref.userName = "${user.firstname.trim()} ${user.lastname.trim()}"
                    pref.userEmail = user.email.trim()
                    pref.token = user.token.trim()
                    pref.userPhone = user.phoneNo.trim()
                    pref.userLocation = user.location.trim()
                    pref.putString("user_profile_image", user.img.trim())

                    showToast("Profile updated successfully!")
                    finish()
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtUpdateProfile.visibility = View.VISIBLE
                    binding.btnUpdate.isEnabled = true
                    showToast(result.message)
                }
            }
        }
    }
}