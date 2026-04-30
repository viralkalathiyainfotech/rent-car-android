package com.example.rentcar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rentcar.base.BaseViewModel
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.model.login.ForgotPasswordResponse
import com.example.rentcar.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {


    private val _forgotPasswordState = MutableLiveData<UiState<ForgotPasswordResponse>>()
    val forgotPasswordState: LiveData<UiState<ForgotPasswordResponse>> = _forgotPasswordState

    fun forgotPassword(email: String) {


        if (email.isBlank()) {
            _forgotPasswordState.value = UiState.Error("Email is required")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _forgotPasswordState.value = UiState.Error("Enter a valid email address")
            return
        }


        _forgotPasswordState.value = UiState.Loading

        viewModelScope.launch {
            val result = repository.forgotPassword(email.trim())
            _forgotPasswordState.postValue(UiState.Success(result))
        }
    }
}