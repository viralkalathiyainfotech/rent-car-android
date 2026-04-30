package com.example.rentcar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentcar.base.utils.NetworkResult
import com.example.rentcar.model.login.ForgotPasswordResponse
import com.example.rentcar.repository.UserRepository
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {

    private val repository = UserRepository()

    private val _forgotPasswordState = MutableLiveData<NetworkResult<ForgotPasswordResponse>>()
    val forgotPasswordState: LiveData<NetworkResult<ForgotPasswordResponse>> = _forgotPasswordState

    fun forgotPassword(email: String) {


        if (email.isBlank()) {
            _forgotPasswordState.value = NetworkResult.Error("Email is required")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _forgotPasswordState.value = NetworkResult.Error("Enter a valid email address")
            return
        }


        _forgotPasswordState.value = NetworkResult.Loading

        viewModelScope.launch {
            val result = repository.forgotPassword(email.trim())
            _forgotPasswordState.postValue(result)
        }
    }
}