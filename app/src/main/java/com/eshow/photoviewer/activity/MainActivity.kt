package com.eshow.photoviewer.activity

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.eshow.photoviewer.ui.theme.PhotoViewerTheme
import com.eshow.photoviewer.view.screen.MainView
import com.eshow.photoviewer.viewmodel.ToolbarViewModel
import com.eshow.photoviewer.viewmodel.UserListViewModel

class MainActivity : ComponentActivity() {
    private val userListViewModel = UserListViewModel()
    private val toolbarViewModel = ToolbarViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        userListViewModel.fetchUsers()

        setContent {
            PhotoViewerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainView(userListViewModel, toolbarViewModel)
                }
            }
        }
    }
}

fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}


