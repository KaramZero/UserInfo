package com.madarsoft.useroutput

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.madarsoft.core.domain.launchIO
import com.madarsoft.core.domain.model.User
import com.madarsoft.core.domain.model.ViewState
import com.madarsoft.core.domain.usecases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserOutputViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {


    private val _viewState = MutableLiveData<ViewState<User>>(ViewState.Idle)
    val viewState: MutableLiveData<ViewState<User>> = _viewState

    init {
        getUser()
    }

    private fun getUser() {
        launchIO {
            _viewState.postValue(ViewState.Loading)
            getUserUseCase.invoke()
                .let {
                    _viewState.postValue(it)
                }
        }
    }

    fun clearViewState() {
        if (_viewState.value !is ViewState.Idle)
            _viewState.postValue(ViewState.Idle)
    }

}