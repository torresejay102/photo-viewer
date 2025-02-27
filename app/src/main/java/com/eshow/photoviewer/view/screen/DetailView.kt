package com.eshow.photoviewer.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.eshow.photoviewer.R
import com.eshow.photoviewer.model.User

@Composable
fun DetailView(user: User?, paddingValues: PaddingValues) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(paddingValues)) {

        if(user!!.photoUrl.isEmpty()) {
            Box(modifier = Modifier.size(width = 400.dp, height = 400.dp),
                contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.size(width = 400.dp, height = 400.dp),
                    painter = painterResource(id = R.drawable.broken_image),
                    contentDescription = stringResource(R.string.image_not_available),
                )
                Text(user.id.toString(), color = Color.Red,
                    fontWeight = FontWeight.Bold, fontSize = 120.sp)
            }
        }
        else {
            SubcomposeAsyncImage(
                modifier = Modifier.size(width = 400.dp, height = 400.dp),
                model = user.photoUrl,
                loading = { CircularProgressIndicator(color = Color.Blue) },
                contentDescription = user.name,
            )
        }

        Text(user.name)
        Text(user.email)
        Text(user.company)
        Text(user.zip)
        Text(user.phone)
        Text(user.company)
        Text(user.address)
        Text(user.state+","+user.country)
    }
}