package com.eshow.photoviewer.view.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.eshow.photoviewer.R


@Composable
fun Dropdown(items: List<String>, action: (String) -> Unit) {
    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableStateOf(1)
    }

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                Text(text = "Sort by: ")
                Text(text = items[itemPosition.value])
                Image(
                    painter = painterResource(R.drawable.dropdown),
                    contentDescription = "DropDown Icon"
                )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(text = {
                        Text(text = item)
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.value = index
                            action(item)
                        })
                }
            }
        }
    }
}