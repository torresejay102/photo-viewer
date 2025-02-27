package com.eshow.photoviewer.view.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
fun ListView(userListViewModel: UserListViewModel, paddingValues: PaddingValues,
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
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items = state.userList, itemContent = {user: User ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.wrapContentHeight().fillMaxWidth().clickable {
                            selectPhoto.invoke(user)
                        }) {
                        if(user.photoUrl.isEmpty()) {
                            Box(modifier = Modifier.size(width = 200.dp, height = 200.dp),
                                contentAlignment = Alignment.Center) {
                                Image(
                                    modifier = Modifier.size(width = 200.dp, height = 200.dp),
                                    painter = painterResource(id = R.drawable.broken_image),
                                    contentDescription = stringResource(R.string.image_not_available),
                                )
                                Text(user.id.toString(), color = Color.Red,
                                    fontWeight = FontWeight.Bold, fontSize = 64.sp)
                            }
                        }
                        else {
                            SubcomposeAsyncImage(
                                modifier = Modifier.size(width = 200.dp, height = 200.dp),
                                model = user.photoUrl,
                                loading = { CircularProgressIndicator(color = Color.Blue) },
                                contentDescription = user.name,
                            )
                        }
                        Text(user.name)
                        Text(user.email)
                        Text(user.company)
                        HorizontalDivider(color = Color.Blue)
                    }

                })
            }
        }
        is RetrievalState.Error -> {
            EmptyImage(paddingValues)
        }
    }
}