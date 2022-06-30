package com.blackend.passwordmanager.ui.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.blackend.passwordmanager.ui.presentation.components.AddPasswordForm
import com.blackend.passwordmanager.ui.presentation.viewmodel.PasswordViewModel
import com.blackend.passwordmanager.ui.theme.AppTheme

@Composable
fun AddPasswordScreen(
    inReadMode: Boolean,
    title: String,
    setTitle: (String) -> Unit,
    userName: String,
    setUserName: (String) -> Unit,
    password: String,
    setPassword: (String) -> Unit,
    viewModel: PasswordViewModel
) {

    val passwordCriteria = viewModel.passwordCriteriaList.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                AppTheme.dimens.grid_2_5,
                AppTheme.dimens.grid_2_5,
                AppTheme.dimens.grid_2_5,
                AppTheme.dimens.grid_0
            )
    ) {
        AddPasswordForm(
            title = title,
            setTitle = setTitle,
            userName = userName,
            setUserName = setUserName,
            password = password,
            setPassword = setPassword,
            inReadMode = inReadMode,
            passwordCriteria = passwordCriteria,
            viewModel = viewModel
        )
    }
}

