package com.blackend.passwordmanager.ui.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackend.passwordmanager.R
import com.blackend.passwordmanager.data.models.PasswordCriteria
import com.blackend.passwordmanager.others.Validator
import com.blackend.passwordmanager.ui.presentation.viewmodel.PasswordViewModel
import com.blackend.passwordmanager.ui.shared.CustomCircularProgressBar
import com.blackend.passwordmanager.ui.theme.AppTheme

@Composable
fun PasswordInput(
    password: String,
    setPassword: (String) -> Unit,
    inReadMode: Boolean,
    criteria: List<PasswordCriteria>,
    viewModel: PasswordViewModel
) {
    val passwordHasError = viewModel.passwordHasError.collectAsState().value
    val focusManager = LocalFocusManager.current
    Text(
        text = "Password",
        fontSize = MaterialTheme.typography.caption.fontSize,
        fontWeight = FontWeight.Light
    )
    Spacer(modifier = Modifier.height(AppTheme.dimens.grid_0_5))
    Row {
        TextField(
            value = password,
            onValueChange = {
                setPassword(it)
                if (passwordHasError.first && Validator.isValidPassword(it)) {
                    viewModel.passwordHasError.value = Pair(false, null)
                }
                Validator.matchCriteria(it, criteria)
            },
            modifier = Modifier.weight(1f),
            trailingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = AppTheme.dimens.grid_2)
                ) {
                    if (inReadMode) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_copy),
                            contentDescription = "Copy Password"
                        )
                        Spacer(modifier = Modifier.width(AppTheme.dimens.grid_1))
                    } else {
                        CustomCircularProgressBar(
                            canvasSize = 32.dp,
                            indicatorValue = password.length,
                            maxIndicatorValue = 16,
                            backgroundIndicatorStrokeWidth = 8f,
                            foregroundIndicatorStrokeWidth = 10f,
                            bigTextFontSize = 10.sp
                        )
                    }
                }
            },
            singleLine = true,
            maxLines = 1,
            readOnly = inReadMode,
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
    }
    if (passwordHasError.first) {
        passwordHasError.second?.let {
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