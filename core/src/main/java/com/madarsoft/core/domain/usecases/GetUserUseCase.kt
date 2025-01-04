package com.madarsoft.core.domain.usecases

import com.madarsoft.core.domain.repo.UserRepository
import javax.inject.Inject


class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.getUser()
}