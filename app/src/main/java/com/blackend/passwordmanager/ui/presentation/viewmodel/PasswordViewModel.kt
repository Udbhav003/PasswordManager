package com.blackend.passwordmanager.ui.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.blackend.passwordmanager.data.models.PasswordCriteria
import com.blackend.passwordmanager.data.models.PasswordItem
import com.blackend.passwordmanager.domain.usecase.*
import com.blackend.passwordmanager.others.InvalidPasswordCodes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val getSavedPasswordsUseCase: GetSavedPasswords,
    private val getPasswordById: GetPasswordById,
    private val savePasswordUseCase: SavePassword,
    private val updatePasswordUseCase: UpdatePassword,
    private val deletePasswordUseCase: DeletePassword
) : ViewModel() {

    var titleHasError: MutableStateFlow<Pair<Boolean, String?>> =
        MutableStateFlow(Pair(false, null))

    var userNameHasError: MutableStateFlow<Pair<Boolean, String?>> =
        MutableStateFlow(Pair(false, null))

    var passwordHasError: MutableStateFlow<Pair<Boolean, String?>> =
        MutableStateFlow(Pair(false, null))

    private var _passwordCriteriaList = MutableStateFlow(
        listOf(
            PasswordCriteria(
                InvalidPasswordCodes.LENGTH_LESS_THAN_EIGHT,
                "A minimum of 8 characters",
                false
            ),
            PasswordCriteria(
                InvalidPasswordCodes.DOES_NOT_CONTAIN_BOTH_UPPERCASE_AND_LOWERCASE_CHAR,
                "Both uppercase and lowercase letters",
                false
            ),
            PasswordCriteria(
                InvalidPasswordCodes.DOES_NOT_CONTAIN_A_NUMBER,
                "At least 1 digit",
                false
            ),
            PasswordCriteria(
                InvalidPasswordCodes.DOES_NOT_HAVE_A_SPECIAL_CHAR,
                "At least 1 special character",
                false
            ),
            PasswordCriteria(
                InvalidPasswordCodes.HAS_LESS_THAN_FIVE_UNIQUE_LETTERS,
                "At least 5 unique characters",
                false
            )
        )
    )

    val passwordCriteriaList get() : StateFlow<List<PasswordCriteria>> = _passwordCriteriaList

    fun getAllPasswords() = getSavedPasswordsUseCase()

    fun getPassword(id: Int): LiveData<PasswordItem> = liveData(Dispatchers.IO) {
        emit(getPasswordById(id))
    }

    fun savePassword(passwordItem: PasswordItem) {
        viewModelScope.launch(Dispatchers.IO) {
            savePasswordUseCase(passwordItem)
        }
    }

    fun updatePassword(passwordItem: PasswordItem) {
        viewModelScope.launch(Dispatchers.IO) {
            updatePasswordUseCase(passwordItem)
        }
    }

    fun deletePassword(passwordItem: PasswordItem) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePasswordUseCase(passwordItem)
        }

    }

}