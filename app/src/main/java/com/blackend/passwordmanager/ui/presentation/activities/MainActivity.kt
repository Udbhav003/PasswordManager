package com.blackend.passwordmanager.ui.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.blackend.passwordmanager.R
import com.blackend.passwordmanager.data.models.PasswordItem
import com.blackend.passwordmanager.others.IntentKeys
import com.blackend.passwordmanager.ui.presentation.screens.AllPasswordsScreen
import com.blackend.passwordmanager.ui.presentation.screens.NoDataFoundScreen
import com.blackend.passwordmanager.ui.presentation.viewmodel.PasswordViewModel
import com.blackend.passwordmanager.ui.shared.AppBar
import com.blackend.passwordmanager.ui.shared.BottomSearchBar
import com.blackend.passwordmanager.ui.theme.PasswordManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: PasswordViewModel by viewModels()
    private var clipboardManager: ClipboardManager? = null

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PasswordManagerTheme {

                val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
                val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
                val coroutineScope = rememberCoroutineScope()

                clipboardManager = LocalClipboardManager.current
                val focusManager = LocalFocusManager.current

                if (sheetState.isCollapsed) {
                    focusManager.clearFocus()
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val passwords =
                        viewModel.getAllPasswords().collectAsState(initial = null).value

                    BottomSheetScaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            AppBar(
                                "Passwords",
                                R.drawable.ic_add
                            ) { navigateToAddPasswordScreen() }
                        },
                        sheetPeekHeight = if (passwords.isNullOrEmpty()) 0.dp else BottomSheetScaffoldDefaults.SheetPeekHeight,
                        sheetContent = {
                            if (!passwords.isNullOrEmpty()) {
                                BottomSearchBar(passwords, {
                                    copyPasswordToClipboard(it)
                                }, {
                                    coroutineScope.launch {
                                        sheetState.expand()
                                    }
                                },
                                    {
                                        navigateToPasswordDetailScreen(it)
                                    })
                            }
                        }) { innerPadding ->

                        Box(modifier = Modifier.padding(innerPadding)) {
                            if (passwords == null) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            } else if (passwords.isNotEmpty()) {
                                AllPasswordsScreen(
                                    passwords = passwords,
                                    isDarkTheme = isSystemInDarkTheme(),
                                    copyPassword = {
                                        copyPasswordToClipboard(it)
                                    },
                                    {
                                        deletePassword(
                                            it,
                                            scaffoldState,
                                            coroutineScope
                                        )
                                    }, {
                                        navigateToPasswordDetailScreen(id = it)
                                    }
                                )
                            } else {
                                NoDataFoundScreen { navigateToAddPasswordScreen() }
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    private fun deletePassword(
        passwordItem: PasswordItem,
        scaffoldState: BottomSheetScaffoldState,
        coroutineScope: CoroutineScope
    ) {
        viewModel.deletePassword(passwordItem)
        coroutineScope.launch {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = "Password Deleted!",
                actionLabel = "Undo"
            )
            when (result) {
                SnackbarResult.Dismissed -> {}
                SnackbarResult.ActionPerformed -> {
                    //val newPasswordItem = passwordItem.copy(id = 0)
                    viewModel.savePassword(passwordItem)
                }
            }
        }
    }

    private fun copyPasswordToClipboard(textToCopy: String) {
        clipboardManager?.setText(AnnotatedString((textToCopy)))
        Toast.makeText(this, "Password Copied!", Toast.LENGTH_LONG).show()
    }

    private fun navigateToAddPasswordScreen() {
        startActivity(
            Intent(this, AddPasswordActivity::class.java).putExtra(
                IntentKeys.READ_ONLY,
                false
            )
        )
    }

    private fun navigateToPasswordDetailScreen(id: Int) {
        startActivity(
            Intent(this, AddPasswordActivity::class.java).putExtra(
                IntentKeys.READ_ONLY,
                true
            ).putExtra(IntentKeys.PASSWORD_ID, id)
        )
    }
}
