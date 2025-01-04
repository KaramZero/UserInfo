package com.madarsoft.core.domain.repo

import com.madarsoft.core.domain.model.User
import com.madarsoft.core.domain.model.ViewState


interface UserRepository {
    suspend fun saveUser(user: User): ViewState<Boolean>
    suspend fun getUser(): ViewState<User>
}