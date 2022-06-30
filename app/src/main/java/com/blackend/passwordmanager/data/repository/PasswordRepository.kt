package com.blackend.passwordmanager.data.repository

import com.blackend.passwordmanager.data.models.PasswordItem
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    fun getAllPasswords(): Flow<List<PasswordItem>>

    suspend fun getPasswordById(id: Int): PasswordItem

    suspend fun savePassword(passwordItem: PasswordItem)

    suspend fun updatePassword(passwordItem: PasswordItem)

    suspend fun deletePassword(passwordItem: PasswordItem)


}