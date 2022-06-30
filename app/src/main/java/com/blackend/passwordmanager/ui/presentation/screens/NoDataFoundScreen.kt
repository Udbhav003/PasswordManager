package com.blackend.passwordmanager.ui.presentation.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.blackend.passwordmanager.R
import com.blackend.passwordmanager.ui.theme.AppTheme
import com.blackend.passwordmanager.ui.theme.Elevation

@Composable
fun NoDataFoundScreen(navigateToAddPasswordScreen: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {

        var targetAlphaValue by remember { mutableStateOf(0f) }

        val scrollState = rememberScrollState()
        val alphaAnim by animateFloatAsState(
            targetValue = targetAlphaValue,
            animationSpec = tween(1000)
        )

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            LaunchedEffect(key1 = targetAlphaValue, block = {
                targetAlphaValue = 1f
            })
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.no_data_found)
                    .crossfade(true)
                    .build(),
                contentDescription = "No Data Found",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .alpha(alphaAnim)
                    .padding(horizontal = AppTheme.dimens.grid_4)
            ) {
                Text(
                    text = "No password added yet.",
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.grid_1))
                Text(
                    text = "We have kept the process simple and clean. Click on the button below to add your first password.",
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .padding(AppTheme.dimens.grid_2, AppTheme.dimens.grid_0)
                )
                Button(shape = RectangleShape,
                    elevation = ButtonDefaults.elevation(defaultElevation = Elevation.none),
                    modifier = Modifier
                        .padding(vertical = AppTheme.dimens.grid_2_5)
                        .fillMaxWidth(), onClick = {
                        navigateToAddPasswordScreen()
                    }) {
                    Text(
                        text = "Add Password",
                        fontSize = MaterialTheme.typography.button.fontSize,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}