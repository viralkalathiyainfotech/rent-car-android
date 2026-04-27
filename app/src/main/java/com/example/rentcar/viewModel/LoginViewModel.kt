package com.example.rentcar.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rentcar.base.BaseViewModel
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.model.login.LoginResponse   // ← LoginResponse use karo
import com.example.rentcar.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {

    private val _loginState = MutableLiveData<UiState<LoginResponse>>()
    val loginState: LiveData<UiState<LoginResponse>> = _loginState

    fun login(email: String, password: String) {

        if (email.isBlank()) {
            _loginState.value = UiState.Error("Email is required")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _loginState.value = UiState.Error("Enter a valid email address")
            return
        }
        if (password.isBlank()) {
            _loginState.value = UiState.Error("Password is required")
            return
        }
        if (password.length < 6) {
            _loginState.value = UiState.Error("Password must be at least 6 characters")
            return
        }

        _loginState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val response = repository.loginUser(email.trim(), password.trim())
                Log.d("LoginVM", "Success: $response")
                _loginState.postValue(UiState.Success(response))
            } catch (e: Exception) {
                Log.e("LoginVM", "Exception: ${e.message}")    // ← shu error aave 6?
                Log.e("LoginVM", "Cause: ${e.cause}")
                _loginState.postValue(UiState.Error(e.message ?: "Unknown error"))
            }
        }
    }
}