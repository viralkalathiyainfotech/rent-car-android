package com.example.rentcar.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentcar.base.utils.NetworkResult
import com.example.rentcar.model.login.RegisterUserResponse
import com.example.rentcar.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = UserRepository()

    private val _loginState = MutableLiveData<NetworkResult<RegisterUserResponse>>()
    val loginState: LiveData<NetworkResult<RegisterUserResponse>> = _loginState


    fun login(email: String, password: String) {


        if (email.isBlank()) {
            _loginState.value = NetworkResult.Error("Email is required")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _loginState.value = NetworkResult.Error("Enter a valid email address")
            return
        }
        if (password.isBlank()) {
            _loginState.value = NetworkResult.Error("Password is required")
            return
        }
        if (password.length < 6) {
            _loginState.value = NetworkResult.Error("Password must be at least 6 characters")
            return
        }


        _loginState.value = NetworkResult.Loading

        viewModelScope.launch {
            val result = repository.loginUser(email.trim(), password.trim())
            _loginState.postValue(result)
        }
    }
}