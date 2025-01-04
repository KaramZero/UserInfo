package com.madarsoft.userinputv2

import androidx.lifecycle.ViewModel
import com.madarsoft.core.domain.launchIO
import com.madarsoft.core.domain.model.Gender
import com.madarsoft.core.domain.model.User
import com.madarsoft.core.domain.model.ViewState
import com.madarsoft.core.domain.usecases.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserInputViewModel @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase,
) : ViewModel() {


    private val _viewState =
        MutableStateFlow<ViewState<Boolean>>(ViewState.Idle)
    private val _userName = MutableStateFlow("")
    private val _age = MutableStateFlow("")
    private val _jobTitle = MutableStateFlow("")
    private val _gender = MutableStateFlow(Gender.MALE)

    val uiState = UiState(
        viewState = _viewState.asStateFlow(),
        onSaveUser = ::saveUser,
        userName = _userName.asStateFlow(),
        age = _age.asStateFlow(),
        jobTitle = _jobTitle.asStateFlow(),
        gender = _gender.asStateFlow(),
        onNameChanged = ::onNameChanged,
        onAgeChanged = ::onAgeChanged,
        onJobTitleChanged = ::onJobTitleChanged,
        onGenderChanged = ::onGenderChanged,
        clearState = ::clearState
    )

    private fun saveUser() {
        launchIO {
            val user = User().apply {
                name = _userName.value
                age = _age.value.toIntOrNull()
                jobTitle = _jobTitle.value
                gender = _gender.value
            }
            if (!isValidUser(user)) {
                _viewState.value = ViewState.InputError("Please fill all fields")
                return@launchIO
            }
            _viewState.value = ViewState.Loading

            saveUserUseCase.invoke(user)
                .let {
                    _viewState.value = it
                }
        }
    }

    private fun isValidUser(user: User): Boolean {
        val res =
            !user.name.isNullOrBlank() && user.age != null && !user.jobTitle.isNullOrBlank() && user.gender != null
        return res
    }

    private fun onNameChanged(name: String) {
        _userName.value = name
    }

    private fun onAgeChanged(age: String) {
        _age.value = age
    }

    private fun onJobTitleChanged(jobTitle: String) {
        _jobTitle.value = jobTitle
    }

    private fun onGenderChanged(gender: Gender?) {
        if (gender != null) {
            _gender.value = gender
        }
    }

    private fun clearState() {
        _viewState.value = ViewState.Idle
    }

    data class UiState(
        val viewState: StateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.Idle),
        val onSaveUser: () -> Unit = {},
        val userName: StateFlow<String> = MutableStateFlow(""),
        val age: StateFlow<String> = MutableStateFlow(""),
        val jobTitle: StateFlow<String> = MutableStateFlow(""),
        val gender: StateFlow<Gender> = MutableStateFlow(Gender.MALE),
        val onNameChanged: (String) -> Unit = {},
        val onAgeChanged: (String) -> Unit = {},
        val onJobTitleChanged: (String) -> Unit = {},
        val onGenderChanged: (Gender?) -> Unit = {},
        val clearState: () -> Unit = {}
    )
}