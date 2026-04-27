package com.example.rentcar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rentcar.base.BaseViewModel
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.model.home.BrandResponseModel
import com.example.rentcar.model.home.CarResponseModel
import com.example.rentcar.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {

    private val _carsState = MutableLiveData<UiState<CarResponseModel>>()
    val carsState: LiveData<UiState<CarResponseModel>> = _carsState

    private val _brandsState = MutableLiveData<UiState<BrandResponseModel>>()
    val brandsState: LiveData<UiState<BrandResponseModel>> = _brandsState

    fun getAllCars() {
        _carsState.value = UiState.Loading
        viewModelScope.launch {
            _carsState.postValue(repository.getAllCars())
        }
    }

    fun getAllBrands() {
        _brandsState.value = UiState.Loading
        viewModelScope.launch {
            _brandsState.postValue(repository.getAllBrands())
        }
    }
}