package com.example.rentcar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentcar.base.utils.NetworkResult
import com.example.rentcar.model.CreateUSerResponse
import com.example.rentcar.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val repository = UserRepository()

    private val _registerState = MutableLiveData<NetworkResult<CreateUSerResponse>>()
    val registerState: LiveData<NetworkResult<CreateUSerResponse>> = _registerState

    fun register(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
        licenceNo: String,
        password: String,
        confirmPassword: String
    ) {
        // ── Validation ────────────────────────────────────
        if (firstname.isBlank()) {
            _registerState.value = NetworkResult.Error("First name is required")
            return
        }
        if (firstname.length < 2) {
            _registerState.value = NetworkResult.Error("First name must be at least 2 characters")
            return
        }
        if (lastname.isBlank()) {
            _registerState.value = NetworkResult.Error("Last name is required")
            return
        }
        if (lastname.length < 2) {
            _registerState.value = NetworkResult.Error("Last name must be at least 2 characters")
            return
        }
        if (email.isBlank()) {
            _registerState.value = NetworkResult.Error("Email is required")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _registerState.value = NetworkResult.Error("Enter a valid email address")
            return
        }
        if (phone.isBlank()) {
            _registerState.value = NetworkResult.Error("Phone number is required")
            return
        }
        if (phone.length < 10) {
            _registerState.value = NetworkResult.Error("Enter a valid phone number")
            return
        }
        if (licenceNo.isBlank()) {
            _registerState.value = NetworkResult.Error("Driving license number is required")
            return
        }
        if (licenceNo.length < 5) {
            _registerState.value = NetworkResult.Error("Enter a valid driving license number")
            return
        }
        if (password.isBlank()) {
            _registerState.value = NetworkResult.Error("Password is required")
            return
        }
        if (password.length < 6) {
            _registerState.value = NetworkResult.Error("Password must be at least 6 characters")
            return
        }
        if (confirmPassword.isBlank()) {
            _registerState.value = NetworkResult.Error("Please confirm your password")
            return
        }
        if (password != confirmPassword) {
            _registerState.value = NetworkResult.Error("Passwords do not match")
            return
        }

        // ── Loading → API call ────────────────────────────
        _registerState.value = NetworkResult.Loading

        viewModelScope.launch {
            val result = repository.registerUser(
                firstname = firstname.trim(),
                lastname = lastname.trim(),
                email = email.trim(),
                phone = phone.trim(),
                licenceNo = licenceNo.trim(),
                password = password.trim()
            )
            _registerState.postValue(result)
        }
    }
}