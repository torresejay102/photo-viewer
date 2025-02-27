package com.eshow.photoviewer.model

import androidx.annotation.DrawableRes

data class Tab(
    val text: String,
    @DrawableRes val image: Int
)