package com.madarsoft.core.domain.usecases

import com.madarsoft.core.domain.model.User
import com.madarsoft.core.domain.repo.UserRepository
import javax.inject.Inject


class SaveUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) = repository.saveUser(user)
}