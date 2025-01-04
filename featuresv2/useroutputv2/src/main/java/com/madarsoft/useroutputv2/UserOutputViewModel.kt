package com.madarsoft.useroutputv2

import androidx.lifecycle.ViewModel
import com.madarsoft.core.domain.launchIO
import com.madarsoft.core.domain.model.User
import com.madarsoft.core.domain.model.ViewState
import com.madarsoft.core.domain.usecases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserOutputViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {


    private val _viewState =
        MutableStateFlow<ViewState<User>>(ViewState.Idle)
    private val _user = MutableStateFlow<User?>(null)

    val uiState = UiState(
        viewState = _viewState.asStateFlow(),
        user = _user.asStateFlow(),
    )

    init {
        getUser()
    }

    private fun getUser() {
        launchIO {
            _viewState.value = ViewState.Loading
            getUserUseCase.invoke()
                .let {
                    _viewState.value = it
                    if (it is ViewState.Success)
                        _user.value = it.data
                }
        }
    }

    data class UiState(
        val viewState: StateFlow<ViewState<User>> = MutableStateFlow(ViewState.Idle),
        val user: StateFlow<User?> = MutableStateFlow(null),
    )
}