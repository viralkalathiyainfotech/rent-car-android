package com.example.rentcar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rentcar.base.BaseViewModel
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.model.login.RegisterUserResponse
import com.example.rentcar.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {

    private val _registerState = MutableLiveData<UiState<RegisterUserResponse>>()
    val registerState: LiveData<UiState<RegisterUserResponse>> = _registerState

    fun register(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
        licenceNo: String,
        password: String,
        confirmPassword: String
    ) {
        if (firstname.isBlank()) { _registerState.value = UiState.Error("First name is required"); return }
        if (firstname.length < 2) { _registerState.value = UiState.Error("First name must be at least 2 characters"); return }
        if (lastname.isBlank()) { _registerState.value = UiState.Error("Last name is required"); return }
        if (lastname.length < 2) { _registerState.value = UiState.Error("Last name must be at least 2 characters"); return }
        if (email.isBlank()) { _registerState.value = UiState.Error("Email is required"); return }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) { _registerState.value = UiState.Error("Enter a valid email address"); return }
        if (phone.isBlank()) { _registerState.value = UiState.Error("Phone number is required"); return }
        if (phone.length < 10) { _registerState.value = UiState.Error("Enter a valid phone number"); return }
        if (licenceNo.isBlank()) { _registerState.value = UiState.Error("Driving license number is required"); return }
        if (licenceNo.length < 5) { _registerState.value = UiState.Error("Enter a valid driving license number"); return }
        if (password.isBlank()) { _registerState.value = UiState.Error("Password is required"); return }
        if (password.length < 6) { _registerState.value = UiState.Error("Password must be at least 6 characters"); return }
        if (confirmPassword.isBlank()) { _registerState.value = UiState.Error("Please confirm your password"); return }
        if (password != confirmPassword) { _registerState.value = UiState.Error("Passwords do not match"); return }

        _registerState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val response = repository.registerUser(
                    firstname = firstname.trim(),
                    lastname  = lastname.trim(),
                    email     = email.trim(),
                    phone     = phone.trim(),
                    licenceNo = licenceNo.trim(),
                    password  = password.trim()
                )
                _registerState.postValue(UiState.Success(response))
            } catch (e: Exception) {
                _registerState.postValue(UiState.Error(e.message ?: "Unknown error"))
            }
        }
    }
}