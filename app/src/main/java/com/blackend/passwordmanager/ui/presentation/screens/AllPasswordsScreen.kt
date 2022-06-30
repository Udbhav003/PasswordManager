package com.blackend.passwordmanager.ui.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.blackend.passwordmanager.data.models.PasswordItem
import com.blackend.passwordmanager.ui.presentation.components.SwippablePasswordItem
import com.blackend.passwordmanager.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllPasswordsScreen(
    passwords: List<PasswordItem>,
    isDarkTheme: Boolean,
    copyPassword: (String) -> Unit,
    deletePassword: (PasswordItem) -> Unit,
    navigateToDetailScreen: (Int) -> Unit
) {

    val groupedItems = passwords.groupBy { it.title.first() }.toSortedMap()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            groupedItems.forEach { (firstLetter, passwords) ->
                item(key = firstLetter) {
                    Text(
                        text = firstLetter.toString(),
                        fontSize = MaterialTheme.typography.subtitle2.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .padding(start = AppTheme.dimens.grid_2_5, top = AppTheme.dimens.grid_2)
                            .fillMaxWidth()
                    )
                }

                items(passwords, key = { item: PasswordItem -> item.id }) { password ->
                    Box(modifier = Modifier.animateItemPlacement()) {
                        SwippablePasswordItem(
                            isDarkTheme = isDarkTheme,
                            password = password,
                            copyPassword = copyPassword,
                            deletePassword = deletePassword,
                            navigateToDetailScreen = navigateToDetailScreen
                        )
                    }
                }
            }
        }
    }


}