package com.blackend.passwordmanager.ui.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.blackend.passwordmanager.R
import com.blackend.passwordmanager.data.models.PasswordItem
import com.blackend.passwordmanager.others.IntentKeys
import com.blackend.passwordmanager.others.InvalidInput
import com.blackend.passwordmanager.others.Validator
import com.blackend.passwordmanager.ui.presentation.screens.AddPasswordScreen
import com.blackend.passwordmanager.ui.presentation.viewmodel.PasswordViewModel
import com.blackend.passwordmanager.ui.shared.AppBar
import com.blackend.passwordmanager.ui.theme.PasswordManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPasswordActivity : ComponentActivity() {

    private val viewModel: PasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerTheme {

                var title by rememberSaveable {
                    mutableStateOf("")
                }

                var userName by rememberSaveable {
                    mutableStateOf("")
                }

                var password by rememberSaveable {
                    mutableStateOf("")
                }

                val readOnly = intent.getBooleanExtra(IntentKeys.READ_ONLY, false)
                val passwordId = intent.getIntExtra(IntentKeys.PASSWORD_ID, -1)

                if (passwordId != -1) {
                    viewModel.getPassword(passwordId).observe(this) {
                        it?.let {
                            title = it.title
                            userName = it.userName
                            password = it.password
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(topBar = {
                        if (readOnly) {
                            AppBar("$title's Password", R.drawable.evernote) {
                                savePassword(
                                    viewModel, title, userName, password, false
                                )
                            }
                        } else {
                            AppBar("Add New Password", R.drawable.ic_check_circle) {
                                savePassword(
                                    viewModel, title, userName, password, true
                                )
                            }
                        }
                    }) {

                        AddPasswordScreen(
                            readOnly,
                            title, { title = it },
                            userName, { userName = it },
                            password, { password = it },
                            viewModel
                        )
                    }
                }
            }
        }
    }

    fun savePassword(
        viewModel: PasswordViewModel,
        title: String,
        userName: String,
        password: String,
        isNewEntry: Boolean
    ) {
        val formValidationResult = Validator.isFormCorrectlyFilled(title, userName, password)
        if (formValidationResult.first) {
            if (isNewEntry) {
                viewModel.savePassword(
                    PasswordItem(
                        title = title,
                        userName = userName,
                        password = password,
                        passwordWillExpire = false
                    )
                )
            } else {

                viewModel.updatePassword(
                    PasswordItem(
                        title = title,
                        userName = userName,
                        password = password,
                        passwordWillExpire = false
                    )
                )
            }

            //Close the activity
            finish()
        } else {
            handleInvalidForm(formValidationResult.second)
        }
    }

    private fun handleInvalidForm(validationResult: InvalidInput) {
        when (validationResult) {
            is InvalidInput.InvalidTitle -> viewModel.titleHasError.value =
                Pair(true, validationResult.msg)
            is InvalidInput.InvalidUserName -> viewModel.userNameHasError.value =
                Pair(true, validationResult.msg)
            is InvalidInput.InvalidPassword -> viewModel.passwordHasError.value =
                Pair(true, validationResult.msg)
            else -> {}
        }
    }

}
