package com.blackend.passwordmanager.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.blackend.passwordmanager.R
import com.blackend.passwordmanager.data.models.PasswordItem
import com.blackend.passwordmanager.ui.presentation.components.PasswordItem
import com.blackend.passwordmanager.ui.theme.AppTheme
import com.blackend.passwordmanager.ui.theme.Elevation

@Composable
fun BottomSearchBar(
    passwords: List<PasswordItem>,
    copyPassword: (String) -> Unit,
    expandSearchBar: () -> Unit,
    navigateToDetailScreen: (Int) -> Unit
) {

    val focusManager = LocalFocusManager.current
    var passwordList by remember {
        mutableStateOf(passwords)
    }

    var searchInput by rememberSaveable {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.surface)
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() })
        { focusManager.clearFocus() }) {
        Card(
            elevation = Elevation.none,
            shape = RectangleShape,
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier
                .height(TextFieldDefaults.MinHeight)
                .fillMaxWidth()
        ) {
            TextField(value = searchInput,
                placeholder = {
                    Text(
                        "Search for passwords..",
                        color = MaterialTheme.colors.onBackground,
                        fontSize = MaterialTheme.typography.button.fontSize,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(top = AppTheme.dimens.grid_0_5)
                            .alpha(ContentAlpha.medium)
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "search icon",
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .alpha(ContentAlpha.medium)
                            .size(AppTheme.dimens.grid_3)
                    )
                },
                textStyle = TextStyle.Default.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = MaterialTheme.typography.button.fontSize,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            expandSearchBar()
                        }
                    },
                onValueChange = {
                    searchInput = it

                    passwordList = if (it.isEmpty()) {
                        passwords
                    } else {
                        searchPassword(it, passwords)
                    }
                })
        }
        Spacer(modifier = Modifier.height(AppTheme.dimens.grid_2_5))
        LazyColumn {
            items(passwordList) {
                PasswordItem(
                    passwordItem = it,
                    copyPassword = copyPassword,
                    navigateToDetailScreen = navigateToDetailScreen
                )
            }
        }
    }
}

fun searchPassword(input: String, passwords: List<PasswordItem>): List<PasswordItem> {
    return passwords.filter { it.title.lowercase().contains(input) }
}