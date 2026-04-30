package com.example.rentcar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.model.MyBookingResponseItem
import com.example.rentcar.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBookingViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _bookingState = MutableLiveData<UiState<List<MyBookingResponseItem>>>()
    val bookingState: LiveData<UiState<List<MyBookingResponseItem>>> = _bookingState

    fun getMyBookings(token: String) {
        if (token.isBlank()) {
            _bookingState.value = UiState.Error("Auth token missing. Please login again.")
            return
        }

        _bookingState.value = UiState.Loading

        viewModelScope.launch {
            val result = repository.getMyBookings(token)
            _bookingState.postValue(result)
        }
    }
}