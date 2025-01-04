package com.madarsoft.userinput

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.madarsoft.core.domain.launchIO
import com.madarsoft.core.domain.model.User
import com.madarsoft.core.domain.model.ViewState
import com.madarsoft.core.domain.usecases.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserInputViewModel @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {


    private val _viewState = MutableLiveData<ViewState<Boolean>>(ViewState.Idle)
    val viewState: MutableLiveData<ViewState<Boolean>> = _viewState


    fun saveUser(user: User) {
        launchIO {
            if (!isValidUser(user)) {
                _viewState.postValue(ViewState.InputError("Please fill all fields"))
                return@launchIO
            }
            _viewState.postValue(ViewState.Loading)
            saveUserUseCase.invoke(user)
                .let {
                    _viewState.postValue(it)
                }
        }
    }

    private fun isValidUser(user: User): Boolean {
        val res = !user.name.isNullOrBlank() && user.age != null && !user.jobTitle.isNullOrBlank() && user.gender != null
        return  res
    }

    fun clearViewState() {
        if (_viewState.value !is ViewState.Idle)
            _viewState.postValue(ViewState.Idle)
    }

}