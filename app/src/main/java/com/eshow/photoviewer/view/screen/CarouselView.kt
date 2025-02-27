package com.eshow.photoviewer.view.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselView(userListViewModel: UserListViewModel, paddingValues: PaddingValues,
                 selectPhoto: ((user: User) -> Unit)) {
    val retrievalState = userListViewModel.retrievalState.collectAsState()
    val context = LocalContext.current.getActivity()

    BackHandler {
        context?.finish()
    }

    when(val state = retrievalState.value) {
        is RetrievalState.Loading -> {
            ProgressDialog()
        }
        is RetrievalState.Success -> {
            val carouselState = rememberCarouselState {
                state.userList.size
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Box(modifier = Modifier.height(350.dp)) {
                    HorizontalUncontainedCarousel (
                        state = carouselState,
                        itemWidth = 200.dp,
                        itemSpacing = 10.dp,
                        contentPadding = PaddingValues(start = 20.dp),
                    ) { index ->
                        val user = state.userList[index]
                        CarouselImage(
                            user = user,
                            modifier = Modifier
                                .maskClip(
                                    MaterialTheme.shapes.extraLarge
                                ),
                            selectPhoto
                        )
                    }
                }
            }
        }
        is RetrievalState.Error -> {
            EmptyImage(paddingValues)
        }
    }
}

@Composable
fun CarouselImage(user: User, modifier: Modifier, selectPhoto: ((user: User) -> Unit)) {
    Column(Modifier.wrapContentWidth().clickable {
        selectPhoto.invoke(user)
    }) {
        if(user.photoUrl.isEmpty()) {
            Box(modifier = Modifier.size(width = 200.dp, height = 200.dp).clickable {
                selectPhoto.invoke(user) }, contentAlignment = Alignment.Center) {
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = modifier.size(width = 200.dp, height = 200.dp),
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
                modifier = modifier.size(width = 200.dp, height = 200.dp),
                contentDescription = null,
                contentScale = ContentScale.None,
            )
        }
    }
}