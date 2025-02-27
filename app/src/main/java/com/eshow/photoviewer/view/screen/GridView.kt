package com.eshow.photoviewer.view.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.eshow.photoviewer.R
import com.eshow.photoviewer.activity.getActivity
import com.eshow.photoviewer.model.User
import com.eshow.photoviewer.state.RetrievalState
import com.eshow.photoviewer.view.custom.EmptyImage
import com.eshow.photoviewer.view.custom.ProgressDialog
import com.eshow.photoviewer.viewmodel.UserListViewModel

@Composable
fun GridView(userListViewModel: UserListViewModel, paddingValues: PaddingValues,
             selectPhoto: ((user: User) -> Unit)) {

    val retrievalState = userListViewModel.retrievalState.collectAsState()
    val context = LocalContext.current.getActivity()

    BackHandler {
        context?.finish()
    }

    when(val state = retrievalState.value) {
        is RetrievalState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProgressDialog()
            }
        }
        is RetrievalState.Success -> {
            LazyVerticalStaggeredGrid(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                columns = StaggeredGridCells.Fixed(3),
                verticalItemSpacing = 6.dp,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                content = {
                    items(items = state.userList, itemContent = { user ->
                        if(user.photoUrl.isEmpty()) {
                            Box(modifier = Modifier.clickable {
                                selectPhoto.invoke(user) }, contentAlignment = Alignment.Center) {
                                Image(
                                    contentScale = ContentScale.Crop,
                                    painter = painterResource(id = R.drawable.broken_image),
                                    contentDescription = stringResource(R.string.image_not_available),
                                )
                                Text(user.id.toString(), color = Color.Red,
                                    fontWeight = FontWeight.Bold, fontSize = 64.sp)
                            }
                        }
                        else {
                            SubcomposeAsyncImage(
                                model = user.photoUrl,
                                loading = { CircularProgressIndicator(color = Color.Blue) },
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    selectPhoto.invoke(user)
                                }
                            )
                        }
                    })
                },
            )
        }
        is RetrievalState.Error -> {
            EmptyImage(paddingValues)
        }
    }
}