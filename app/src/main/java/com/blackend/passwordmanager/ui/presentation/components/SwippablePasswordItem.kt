package com.blackend.passwordmanager.ui.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.blackend.passwordmanager.data.models.PasswordItem
import com.blackend.passwordmanager.ui.shared.RevealDirection
import com.blackend.passwordmanager.ui.shared.RevealSwipe
import com.blackend.passwordmanager.ui.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwippablePasswordItem(
    isDarkTheme: Boolean,
    password: PasswordItem,
    copyPassword: (String) -> Unit,
    deletePassword: (PasswordItem) -> Unit,
    navigateToDetailScreen: (Int) -> Unit
) {
    RevealSwipe(
        modifier = Modifier.padding(vertical = AppTheme.dimens.grid_0_5),
        backgroundCardEndColor = MaterialTheme.colors.error,
        shape = RoundedCornerShape(AppTheme.dimens.grid_0),
        directions = setOf(
            RevealDirection.EndToStart
        ),
        hiddenContentEnd = {
            Icon(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimens.grid_4_5),
                imageVector = Icons.Outlined.Delete,
                tint = Color.White,
                contentDescription = null
            )
        },
        onBackgroundEndClick = {
            deletePassword(password)
        }
    ) {
        PasswordItem(password, isDarkTheme, copyPassword, navigateToDetailScreen)
    }
}