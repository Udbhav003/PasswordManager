package com.blackend.passwordmanager.domain.usecase

import com.blackend.passwordmanager.data.models.PasswordItem
import com.blackend.passwordmanager.data.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow

class GetSavedPasswords(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(): Flow<List<PasswordItem>> = passwordRepository.getAllPasswords()
}