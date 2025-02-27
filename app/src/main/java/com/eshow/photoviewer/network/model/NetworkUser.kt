package com.eshow.photoviewer.network.model

import com.eshow.photoviewer.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkUser(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String? = null,
    @SerialName("company") val company: String? = null,
    @SerialName("username") val username: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("zip") val zip: String? = null,
    @SerialName("state") val state: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("photo") val photo: String? = null)

fun NetworkUser.toUser() : User {
    return User(id, name.orEmpty(), company.orEmpty(),
        username.orEmpty(), email.orEmpty(), address.orEmpty(), zip.orEmpty(),
        state.orEmpty(), country.orEmpty(), phone.orEmpty(), photo.orEmpty())
}