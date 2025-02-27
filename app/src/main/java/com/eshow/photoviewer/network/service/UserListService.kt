package com.eshow.photoviewer.network.service

import com.eshow.photoviewer.network.model.NetworkUser
import retrofit2.http.GET

interface UserListService {
    @GET("users")
    suspend fun getUsers(): List<NetworkUser>
}