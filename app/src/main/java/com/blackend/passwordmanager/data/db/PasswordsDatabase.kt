package com.blackend.passwordmanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blackend.passwordmanager.data.models.PasswordItem

@Database(
    entities = [PasswordItem::class],
    version = 1,
    exportSchema = false
)
abstract class PasswordsDatabase : RoomDatabase() {
    abstract fun passwordDAO(): PasswordDAO

    companion object {
        const val DATABASE_NAME = "passwords_db"
    }
}
