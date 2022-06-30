package com.blackend.passwordmanager.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.blackend.passwordmanager.R
import com.blackend.passwordmanager.data.models.PasswordItem
import com.blackend.passwordmanager.others.SiteMappings
import com.blackend.passwordmanager.ui.theme.AppTheme

@Composable
fun PasswordItem(
    passwordItem: PasswordItem,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    copyPassword: (String) -> Unit,
    navigateToDetailScreen: (Int) -> Unit
) {
    val icon: Int = if (passwordItem.title.lowercase().contains("bank")) {
        R.drawable.bank
    } else {
        SiteMappings.siteMap[passwordItem.title.lowercase()] ?: R.drawable.website
    }

    Column(modifier = Modifier
        .background(MaterialTheme.colors.surface)
        .clickable {
            //Navigate to password detail screen
            navigateToDetailScreen(passwordItem.id)
        }) {
        Spacer(modifier = Modifier.height(AppTheme.dimens.grid_1_5))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(AppTheme.dimens.grid_2))
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(AppTheme.dimens.grid_1_5))
                    .background(
                        if (!isDarkTheme)
                            Color.Black.copy(0.04f)
                        else
                            Color.White.copy(0.04f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(icon)
                        .build(),
                    contentDescription = "Logo",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground.copy(0.8f)),
                    modifier = Modifier
                        .alpha(0.8f)
                        .size(AppTheme.dimens.grid_4)
                )
            }
            Spacer(modifier = Modifier.width(AppTheme.dimens.grid_1_5))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = passwordItem.title,
                    fontSize = MaterialTheme.typography.subtitle2.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onBackground.copy(0.9f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.grid_1))
                Text(
                    text = passwordItem.userName,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    fontWeight = FontWeight.Light,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .alpha(0.7f)
                )
            }
            Box(modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    copyPassword(passwordItem.password)
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription = "Copy password",
                    modifier = Modifier
                        .alpha(0.7f)
                        .padding(AppTheme.dimens.grid_2)
                )
            }
        }
        Spacer(modifier = Modifier.height(AppTheme.dimens.grid_1_5))
    }
}


