package com.blackend.passwordmanager.ui.presentation.di

import com.blackend.passwordmanager.data.db.PasswordDAO
import com.blackend.passwordmanager.data.repository.PasswordRepository
import com.blackend.passwordmanager.domain.repository.PasswordRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesPasswordRepository(passwordDAO: PasswordDAO): PasswordRepository {
        return PasswordRepositoryImpl(passwordDAO)
    }
}