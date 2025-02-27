package com.eshow.photoviewer.view.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eshow.photoviewer.R
import com.eshow.photoviewer.model.Tab
import com.eshow.photoviewer.model.User
import com.eshow.photoviewer.ui.theme.PhotoViewerTheme
import com.eshow.photoviewer.view.custom.NavigationBarView
import com.eshow.photoviewer.view.custom.Toolbar
import com.eshow.photoviewer.viewmodel.ToolbarViewModel
import com.eshow.photoviewer.viewmodel.UserListViewModel

@Composable
fun MainView(userListViewModel: UserListViewModel, toolbarViewModel: ToolbarViewModel) {
    val carouselTab = Tab(text = stringResource(R.string.carousel), image = R.drawable.carousel)
    val listTab = Tab(text = stringResource(R.string.list), image = R.drawable.list)
    val gridTab = Tab(text = stringResource(R.string.grid), image = R.drawable.grid)
    val tabs = listOf(carouselTab, listTab, gridTab)
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedUser: User? by remember { mutableStateOf(null) }

    fun selectPhoto(user: User) {
        selectedUser = user
        navController.navigate("details")
    }

    fun updateToolbar(title: String) {
        toolbarViewModel.setTitle(title)
        toolbarViewModel.setShowBack((title) == "Details")
    }

    PhotoViewerTheme (darkTheme = false) {
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Scaffold(
                topBar = { Toolbar(navController = navController,
                    toolbarViewModel = toolbarViewModel,
                    refresh = {userListViewModel.fetchUsers()}) },
                bottomBar = { NavigationBarView(tabs, navController) },
                snackbarHost = { SnackbarHost(snackbarHostState) } ) { padding ->
                NavHost(navController = navController, startDestination = carouselTab.text) {
                    composable(carouselTab.text) {
                        updateToolbar("Carousel")
                        CarouselView(userListViewModel, padding, selectPhoto = { user ->
                            selectPhoto(user)
                        })
                    }
                    composable(listTab.text) {
                        updateToolbar("List")
                        ListView(userListViewModel, padding, selectPhoto = { user ->
                            selectPhoto(user)
                        })
                    }
                    composable(gridTab.text) {
                        updateToolbar("Grid")
                        GridView(userListViewModel, padding, selectPhoto = { user ->
                            selectPhoto(user)
                        })
                    }
                    composable("details") {
                        updateToolbar("Details")
                        DetailView(selectedUser, padding)
                    }
                }
            }
        }
    }
}