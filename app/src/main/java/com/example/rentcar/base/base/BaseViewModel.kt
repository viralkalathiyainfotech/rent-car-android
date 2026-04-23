package com.example.rentcar.base.base

// base/BaseViewModel.kt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val successMessage = MutableLiveData<String>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        isLoading.postValue(false)
        errorMessage.postValue(throwable.message ?: "Something went wrong")
    }

    protected fun launchOnIO(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            block()
        }
    }

    protected fun launchOnMain(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            block()
        }
    }

    protected fun <T> launchWithLoading(block: suspend () -> T) {
        viewModelScope.launch(exceptionHandler) {
            isLoading.value = true
            try {
                withContext(Dispatchers.IO) {
                    block()
                }
            } finally {
                isLoading.value = false
            }
        }
    }
}