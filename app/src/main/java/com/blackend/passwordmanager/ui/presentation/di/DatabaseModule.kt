package com.blackend.passwordmanager.ui.presentation.di

import android.content.Context
import androidx.room.Room
import com.blackend.passwordmanager.data.db.PasswordDAO
import com.blackend.passwordmanager.data.db.PasswordsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesRoomDatabase(@ApplicationContext context: Context): PasswordsDatabase {
        return Room.databaseBuilder(
            context,
            PasswordsDatabase::class.java,
            PasswordsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesPasswordDAO(passwordsDatabase: PasswordsDatabase): PasswordDAO {
        return passwordsDatabase.passwordDAO()
    }
}