package com.madarsoft.data.repository

import com.madarsoft.core.domain.model.ViewState
import com.madarsoft.core.domain.repo.UserRepository
import com.madarsoft.data.datasource.local.UserDao
import com.madarsoft.data.model.User
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepositoryImpl @Inject constructor(
    private val localSource: UserDao
) : UserRepository {

    override suspend fun saveUser(user: com.madarsoft.core.domain.model.User): ViewState<Boolean> =
        try {
            localSource.insertUser(User from user)
            ViewState.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            ViewState.Error(e.message ?: "An error occurred")
        }


    override suspend fun getUser(): ViewState<com.madarsoft.core.domain.model.User> =
        try {
            val user = localSource.getUser()
            ViewState.Success(User to user)
        } catch (e: Exception) {
            e.printStackTrace()
            ViewState.Error(e.message ?: "An error occurred")
        }

}