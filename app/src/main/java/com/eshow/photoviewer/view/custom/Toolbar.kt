package com.eshow.photoviewer.view.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.eshow.photoviewer.R
import com.eshow.photoviewer.viewmodel.ToolbarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(navController: NavController,
            toolbarViewModel: ToolbarViewModel, color: Color = Color.Blue,
            refresh: (() -> Unit)? = null) {

    val titleState = toolbarViewModel.titleState.collectAsState()
    val showBackState = toolbarViewModel.showBackState.collectAsState()

    CenterAlignedTopAppBar(
        title = {
            Text(
                titleState.value,
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp
                )
            )
        },
        navigationIcon = {
            if (showBackState.value) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color.White),
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .clickable { navController.navigateUp() }
                        .fillMaxHeight()
                        .width(width = 50.dp)
                )
            }
            else {
                Image(
                    painter = painterResource(id = R.drawable.refresh),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color.White),
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .clickable { refresh?.invoke() }
                        .fillMaxHeight()
                        .width(width = 50.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = color
        )
    )
}