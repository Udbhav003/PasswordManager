package com.blackend.passwordmanager.ui.presentation.di

import com.blackend.passwordmanager.data.repository.PasswordRepository
import com.blackend.passwordmanager.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsecaseModule {

    @Provides
    @Singleton
    fun providesGetSavedPasswordsUseCase(passwordRepository: PasswordRepository): GetSavedPasswords {
        return GetSavedPasswords(passwordRepository)
    }

    @Provides
    @Singleton
    fun providesGetPasswordByIdUseCase(passwordRepository: PasswordRepository): GetPasswordById {
        return GetPasswordById(passwordRepository)
    }

    @Provides
    @Singleton
    fun providesSavePasswordUseCase(passwordRepository: PasswordRepository): SavePassword {
        return SavePassword(passwordRepository)
    }

    @Provides
    @Singleton
    fun providesUpdatePasswordUseCase(passwordRepository: PasswordRepository): UpdatePassword {
        return UpdatePassword(passwordRepository)
    }

    @Provides
    @Singleton
    fun providesDeletePasswordUseCase(passwordRepository: PasswordRepository): DeletePassword {
        return DeletePassword(passwordRepository)
    }
}