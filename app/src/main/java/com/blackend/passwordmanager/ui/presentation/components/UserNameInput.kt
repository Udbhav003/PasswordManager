package com.blackend.passwordmanager.ui.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.blackend.passwordmanager.others.Validator
import com.blackend.passwordmanager.ui.presentation.viewmodel.PasswordViewModel
import com.blackend.passwordmanager.ui.theme.AppTheme

@Composable
fun UserNameInput(
    userName: String,
    setUserName: (String) -> Unit,
    viewModel: PasswordViewModel,
    inReadMode: Boolean
) {

    val userNameHasError = viewModel.userNameHasError.collectAsState().value
    val focusManager = LocalFocusManager.current

    Text(
        text = "Username",
        fontSize = MaterialTheme.typography.caption.fontSize,
        fontWeight = FontWeight.Light
    )
    Spacer(modifier = Modifier.height(5.dp))
    TextField(
        value = userName,
        onValueChange = {
            setUserName(it)
            if (userNameHasError.first && Validator.isValidUserName(it)) {
                viewModel.userNameHasError.value = Pair(false, null)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        maxLines = 1,
        readOnly = inReadMode,
        isError = userNameHasError.first,
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = false,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
    if (userNameHasError.first) {
        userNameHasError.second?.let {
            Spacer(modifier = Modifier.height(AppTheme.dimens.grid_0_25))
            Text(
                text = it,
                fontSize = MaterialTheme.typography.overline.fontSize,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colors.error
            )
        }
    }
}