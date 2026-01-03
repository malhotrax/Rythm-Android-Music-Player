package com.player.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.player.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(
    modifier: Modifier = Modifier,
    onSearch: () -> Unit,
    title: String
) {
    TopAppBar(
        modifier = modifier
            .padding(8.dp)
            .height(70.dp)
        ,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        actions = {
            IconButton(
                onClick = onSearch
            ) {
                Icon(painter = painterResource(R.drawable.search_icon), "Search song")
            }
        }
    )
}