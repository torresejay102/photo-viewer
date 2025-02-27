package com.eshow.photoviewer.state

import com.eshow.photoviewer.model.User

sealed class RetrievalState {
    object Loading : RetrievalState()
    data class Success(val userList: List<User>) : RetrievalState()
    data class Error(val errorMessage: String) : RetrievalState()
}