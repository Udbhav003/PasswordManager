package com.blackend.passwordmanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Color.White,
    primaryVariant = Purple700,
    secondary = Teal200,
    error = Error
)

private val LightColorPalette = lightColors(
    primary = Color.Black,
    primaryVariant = Purple700,
    secondary = Teal200,
    error = Error
)

private fun setStatusBarColor(systemUiController: SystemUiController, isDarkTheme: Boolean) {
    if (isDarkTheme) {
        systemUiController.setStatusBarColor(
            color = DarkColorPalette.background,
            darkIcons = false
        ) {
            Color.Transparent
        }
    } else {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        ) {
            Color.LightGray
        }
    }
}

@Composable
fun PasswordManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    setStatusBarColor(systemUiController, darkTheme)

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val configuration = LocalConfiguration.current

    val dimensions = if (configuration.screenWidthDp <= 360)
        smallDimensions
    else
        sw360Dimensions

    val typography = if (configuration.screenWidthDp <= 360)
        smallTypography
    else
        sw360Typography

    ProvideDimens(dimensions = dimensions) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = Shapes,
            content = content
        )
    }
}
