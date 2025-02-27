package com.eshow.photoviewer.view.custom

import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.eshow.photoviewer.model.Tab

@Composable
fun NavigationBarView(tabs: List<Tab>, navController: NavController) {
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar {
        tabs.forEachIndexed { index, tab ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(tab.text)
                },
                icon = {
                    Image(
                        painter = painterResource(tab.image),
                        colorFilter = ColorFilter.tint(if(selectedTabIndex == index)
                            Color.Blue else Color.Black, blendMode = BlendMode.SrcIn),
                        contentDescription = tab.text
                    )
                },
                label = {
                    Text(tab.text, color = if(selectedTabIndex == index)
                        Color.Blue else Color.Black)
                })
        }
    }
}