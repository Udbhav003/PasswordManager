package com.blackend.passwordmanager.data.db

import androidx.room.*
import com.blackend.passwordmanager.data.models.PasswordItem
import com.blackend.passwordmanager.others.Entities
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PasswordDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(passwordItem: PasswordItem)

    suspend fun savePassword(passwordItem: PasswordItem) {
        save(passwordItem.apply {
            createdAt = System.currentTimeMillis()
            modifiedAt = System.currentTimeMillis()
        })
    }

    @Update
    abstract suspend fun update(passwordItem: PasswordItem)

    @Update
    suspend fun updatePassword(passwordItem: PasswordItem) {
        update(passwordItem.apply {
            createdAt = System.currentTimeMillis()
            modifiedAt = System.currentTimeMillis()
        })
    }

    @Query("SELECT * FROM ${Entities.Passwords}")
    abstract fun getAllPasswords(): Flow<List<PasswordItem>>

    @Query("SELECT * FROM ${Entities.Passwords} WHERE id = :id")
    abstract suspend fun getPasswordById(id: Int): PasswordItem

    @Delete
    abstract suspend fun deletePassword(passwordItem: PasswordItem)
}