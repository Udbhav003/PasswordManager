package com.blackend.passwordmanager.ui.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.blackend.passwordmanager.others.Validator
import com.blackend.passwordmanager.ui.presentation.viewmodel.PasswordViewModel
import com.blackend.passwordmanager.ui.theme.AppTheme

@Composable
fun TitleInput(
    title: String,
    setTitle: (String) -> Unit,
    viewModel: PasswordViewModel,
    inReadMode: Boolean
) {
    val titleHasError = viewModel.titleHasError.collectAsState().value

    Text(
        text = "Title",
        fontSize = MaterialTheme.typography.caption.fontSize,
        fontWeight = FontWeight.Light
    )
    Spacer(modifier = Modifier.height(5.dp))
    TextField(
        value = title,
        onValueChange = {
            setTitle(it)
            if (titleHasError.first && Validator.isValidTitle(it)) {
                viewModel.titleHasError.value = Pair(false, null)
            }
        },
        modifier = Modifier.fillMaxWidth(0.8f),
        singleLine = true,
        maxLines = 1,
        readOnly = inReadMode,
        isError = titleHasError.first,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    )
    if (titleHasError.first) {
        titleHasError.second?.let {
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