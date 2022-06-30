package com.blackend.passwordmanager.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blackend.passwordmanager.others.Entities

@Entity(tableName = Entities.Passwords)
data class PasswordItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val userName: String,
    val password: String,
    var createdAt: Long = -1,
    var modifiedAt: Long = -1,
    val isPinned: Boolean = false,
    val passwordWillExpire: Boolean,
    val timeToExpire: Long? = null
)
