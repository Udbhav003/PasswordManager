package com.blackend.passwordmanager.domain.usecase

import com.blackend.passwordmanager.data.models.PasswordItem
import com.blackend.passwordmanager.data.repository.PasswordRepository

class UpdatePassword(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(passwordItem: PasswordItem) =
        passwordRepository.updatePassword(passwordItem)
}