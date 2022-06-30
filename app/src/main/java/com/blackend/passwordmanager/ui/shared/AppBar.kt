package com.blackend.passwordmanager.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.blackend.passwordmanager.ui.theme.AppTheme
import com.blackend.passwordmanager.ui.theme.Elevation

@Composable
fun AppBar(title: String, icon: Int?, onClickIcon: () -> Unit) {
    TopAppBar(backgroundColor = MaterialTheme.colors.background, elevation = Elevation.none) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(AppTheme.dimens.grid_2_5, AppTheme.dimens.grid_0)
        ) {
            Text(
                text = title,
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onBackground.copy(ContentAlpha.medium),
                modifier = Modifier.weight(1f)
            )
            icon?.let {
                Surface(modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onClickIcon()
                    }) {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "App Bar Icon",
                        modifier = Modifier
                            .size(AppTheme.dimens.grid_4)
                    )
                }
            }

        }
    }
}
