package com.madarsoft.core.domain.model

sealed class ViewState<out T> {
    data object Idle : ViewState<Nothing>()
    data object Loading : ViewState<Nothing>()
    data class Success<T>(val data: T) : ViewState<T>()
    data class Error(val message: String) : ViewState<Nothing>()
    data class InputError(val message: String) : ViewState<Nothing>()
}

