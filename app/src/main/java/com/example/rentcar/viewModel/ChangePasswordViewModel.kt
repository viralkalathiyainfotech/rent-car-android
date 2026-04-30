package com.example.rentcar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.model.login.ChangePasswordResponse
import com.example.rentcar.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _changePasswordState = MutableLiveData<UiState<ChangePasswordResponse>>()
    val changePasswordState: LiveData<UiState<ChangePasswordResponse>> = _changePasswordState

    fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ) {

        if (oldPassword.isBlank()) {
            _changePasswordState.value = UiState.Error("Old password is required")
            return
        }
        if (oldPassword.length < 6) {
            _changePasswordState.value = UiState.Error("Old password must be at least 6 characters")
            return
        }
        if (newPassword.isBlank()) {
            _changePasswordState.value = UiState.Error("New password is required")
            return
        }
        if (newPassword.length < 6) {
            _changePasswordState.value = UiState.Error("New password must be at least 6 characters")
            return
        }
        if (confirmPassword.isBlank()) {
            _changePasswordState.value = UiState.Error("Please confirm your new password")
            return
        }
        if (newPassword != confirmPassword) {
            _changePasswordState.value =
                UiState.Error("New password and confirm password do not match")
            return
        }
        if (oldPassword == newPassword) {
            _changePasswordState.value =
                UiState.Error("New password must be different from old password")
            return
        }

        _changePasswordState.value = UiState.Loading

        viewModelScope.launch {
            val result = repository.changePassword(
                token = token,
                oldPassword = oldPassword.trim(),
                newPassword = newPassword.trim(),
                confirmPassword = confirmPassword.trim()
            )
            _changePasswordState.postValue(UiState.Success(result))
        }
    }
}