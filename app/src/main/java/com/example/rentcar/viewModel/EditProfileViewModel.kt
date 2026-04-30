package com.example.rentcar.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
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
import okhttp3.RequestBody.Companion.toRequestBody
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
        imageUri: Uri?
    ) {
        // Validation
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

        _updateState.value = UiState.Loading

        viewModelScope.launch {
            try {
                // Step 1: Text parts
                val firstNamePart = firstName.trim().toRequestBody("text/plain".toMediaTypeOrNull())
                val lastNamePart = lastName.trim().toRequestBody("text/plain".toMediaTypeOrNull())
                val phonePart = phone.trim().toRequestBody("text/plain".toMediaTypeOrNull())

                // Step 2: Image part (null if no image selected)
                val photoPart: MultipartBody.Part? = imageUri?.let { uri ->
                    val file = uriToFile(context, uri)
                    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("photo", file.name, requestBody)
                }

                // Step 3: API call
                val response = repository.updateProfile(
                    token = token,
                    firstName = firstNamePart.toString(),
                    lastName = lastNamePart.toString(),
                    phone = phonePart.toString(),
                    imgPart = photoPart
                )

                Log.d("EditProfileVM", "updateProfile success | photo: $photoPart")
                _updateState.value = (response)

            } catch (e: Exception) {
                Log.e("EditProfileVM", "updateProfile error: ${e.message}")
                _updateState.postValue(UiState.Error(e.message ?: "Update failed"))
            }
        }
    }
}

private fun uriToFile(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val tempFile = File(context.cacheDir, "profile_${System.currentTimeMillis()}.jpg")
    val outputStream = FileOutputStream(tempFile)
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()
    return tempFile
}
