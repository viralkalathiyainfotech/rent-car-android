package com.example.rentcar.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.model.UpdateProfileResponse
import com.example.rentcar.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _updateState = MutableLiveData<UiState<UpdateProfileResponse>>()
    val updateState: LiveData<UiState<UpdateProfileResponse>> = _updateState

    private val _selectedImageUri = MutableLiveData<Uri?>()
    val selectedImageUri: LiveData<Uri?> = _selectedImageUri

    fun setSelectedImage(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun updateProfile(
        context: Context,
        token: String,
        firstName: String,
        lastName: String,
        phone: String,
        location: String,
        imageUri: Uri?
    ) {
        if (firstName.isBlank()) {
            _updateState.value = UiState.Error("First name is required")
            return
        }
        if (lastName.isBlank()) {
            _updateState.value = UiState.Error("Last name is required")
            return
        }
        if (phone.isBlank()) {
            _updateState.value = UiState.Error("Phone number is required")
            return
        }
        if (phone.length < 10) {
            _updateState.value = UiState.Error("Enter a valid phone number")
            return
        }
        if (location.isBlank()) {
            _updateState.value = UiState.Error("Location is required")
            return
        }

        _updateState.value = UiState.Loading

        viewModelScope.launch {
            val imgPart: MultipartBody.Part? = imageUri?.let { uri ->
                try {
                    val inputStream = context.contentResolver.openInputStream(uri)
                        ?: return@let null
                    val tempFile =
                        File(context.cacheDir, "profile_${System.currentTimeMillis()}.jpg")
                    val outputStream = FileOutputStream(tempFile)
                    inputStream.copyTo(outputStream)
                    inputStream.close()
                    outputStream.close()

                    if (tempFile.length() == 0L) return@let null

                    val requestBody = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("img", tempFile.name, requestBody)
                } catch (e: Exception) {
                    null
                }
            }

            val result = repository.updateProfile(
                token = token,
                firstName = firstName.trim(),
                lastName = lastName.trim(),
                phone = phone.trim(),
                location = location.trim(),
                imgPart = imgPart
            )
            _updateState.postValue(result)
        }
    }
}