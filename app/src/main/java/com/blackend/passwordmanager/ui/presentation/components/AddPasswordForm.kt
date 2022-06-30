package com.blackend.passwordmanager.ui.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import com.blackend.passwordmanager.data.models.PasswordCriteria
import com.blackend.passwordmanager.others.PasswordManager
import com.blackend.passwordmanager.others.Validator
import com.blackend.passwordmanager.ui.presentation.viewmodel.PasswordViewModel
import com.blackend.passwordmanager.ui.theme.AppTheme

@Composable
fun AddPasswordForm(
    title: String,
    setTitle: (String) -> Unit,
    userName: String,
    setUserName: (String) -> Unit,
    password: String,
    setPassword: (String) -> Unit,
    inReadMode: Boolean,
    passwordCriteria: List<PasswordCriteria>,
    viewModel: PasswordViewModel
) {
    val focusManager = LocalFocusManager.current

    if (inReadMode)
        Validator.matchCriteria(password, passwordCriteria)

    Column {
        LazyColumn(
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                { focusManager.clearFocus() }
        ) {
            item {
                TitleInput(
                    title = title,
                    setTitle = setTitle,
                    viewModel = viewModel,
                    inReadMode = inReadMode
                )

                Spacer(modifier = Modifier.height(AppTheme.dimens.grid_2_5))

                UserNameInput(
                    userName = userName,
                    setUserName = setUserName,
                    viewModel = viewModel,
                    inReadMode = inReadMode
                )

                Spacer(modifier = Modifier.height(AppTheme.dimens.grid_1_5))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.dimens.grid_2_5)
                        .padding(top = AppTheme.dimens.grid_1_5)
                        .clip(shape = RoundedCornerShape(topStart = AppTheme.dimens.grid_1_5))
                        .background(
                            if (!isSystemInDarkTheme())
                                Color.Black.copy(0.04f)
                            else
                                Color.White.copy(0.04f)
                        )
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (!isSystemInDarkTheme())
                                Color.Black.copy(0.04f)
                            else
                                Color.White.copy(0.04f)
                        )
                        .padding(horizontal = AppTheme.dimens.grid_2)
                ) {
                    Text(
                        text = "A good password must contain",
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = AppTheme.dimens.grid_1)
                    )
                }
            }
            passwordCriteria.forEach {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (!isSystemInDarkTheme())
                                    Color.Black.copy(0.04f)
                                else
                                    Color.White.copy(0.04f)
                            )
                            .padding(horizontal = AppTheme.dimens.grid_2)
                    ) {
                        Row(
                            Modifier.padding(AppTheme.dimens.grid_0_5),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val alphaAnim by animateFloatAsState(
                                targetValue = if (it.criteriaMet) 1f else 0f,
                                animationSpec = tween(1000)
                            )
                            Canvas(
                                modifier = Modifier
                                    .padding(
                                        start = AppTheme.dimens.grid_0_5,
                                        end = AppTheme.dimens.grid_1
                                    )
                                    .size(AppTheme.dimens.grid_1)
                            ) {
                                drawCircle(Color.DarkGray)
                            }
                            Text(
                                text = it.criteria,
                                fontSize = MaterialTheme.typography.overline.fontSize,
                                fontWeight = FontWeight.Light
                            )
                            Spacer(modifier = Modifier.width(AppTheme.dimens.grid_0_5))
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Criteria matched icon",
                                modifier = Modifier
                                    .size(AppTheme.dimens.grid_2)
                                    .alpha(alphaAnim)
                            )
                        }
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.dimens.grid_2_5)
                        .padding(bottom = AppTheme.dimens.grid_1_5)
                        .clip(shape = RoundedCornerShape(bottomEnd = AppTheme.dimens.grid_1_5))
                        .background(
                            if (!isSystemInDarkTheme())
                                Color.Black.copy(0.04f)
                            else
                                Color.White.copy(0.04f)
                        )
                )

                PasswordInput(
                    password = password,
                    setPassword = setPassword,
                    inReadMode = inReadMode,
                    criteria = passwordCriteria,
                    viewModel = viewModel
                )

                if (!inReadMode) {
                    OutlinedButton(
                        modifier = Modifier
                            .padding(
                                top = AppTheme.dimens.grid_1_5,
                                bottom = AppTheme.dimens.grid_2_5
                            )
                            .fillMaxWidth(), onClick = {
                            var generatedPassword: String
                            do {
                                generatedPassword = PasswordManager.generatePassword(
                                    isWithLetters = true,
                                    isWithUppercase = true,
                                    isWithNumbers = true,
                                    isWithSpecial = true,
                                    length = 16
                                )
                            } while (!PasswordManager.evaluatePassword(generatedPassword))

                            setPassword(generatedPassword)
                            if (viewModel.passwordHasError.value.first && Validator.isValidPassword(
                                    generatedPassword
                                )
                            ) {
                                viewModel.passwordHasError.value = Pair(false, null)
                            }
                            Validator.matchCriteria(generatedPassword, passwordCriteria)
                        }) {
                        Text(
                            text = "Generate Password",
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

            }
        }


    }
}



