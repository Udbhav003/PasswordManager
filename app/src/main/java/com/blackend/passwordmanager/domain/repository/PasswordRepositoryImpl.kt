package com.blackend.passwordmanager.domain.repository

import com.blackend.passwordmanager.data.db.PasswordDAO
import com.blackend.passwordmanager.data.models.PasswordItem
import com.blackend.passwordmanager.data.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow

class PasswordRepositoryImpl(
    private val passwordDAO: PasswordDAO
) : PasswordRepository {
    override fun getAllPasswords(): Flow<List<PasswordItem>> = passwordDAO.getAllPasswords()

    override suspend fun getPasswordById(id: Int): PasswordItem {
        return passwordDAO.getPasswordById(id)
    }

    override suspend fun savePassword(passwordItem: PasswordItem) {
        return passwordDAO.savePassword(passwordItem)
    }

    override suspend fun updatePassword(passwordItem: PasswordItem) {
        return passwordDAO.updatePassword(passwordItem)
    }

    override suspend fun deletePassword(passwordItem: PasswordItem) {
        return passwordDAO.deletePassword(passwordItem)
    }
}