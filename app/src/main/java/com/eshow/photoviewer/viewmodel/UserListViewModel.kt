package com.eshow.photoviewer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshow.photoviewer.model.User
import com.eshow.photoviewer.network.model.toUser
import com.eshow.photoviewer.network.service.NetworkService
import com.eshow.photoviewer.state.RetrievalState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserListViewModel: ViewModel() {
    private val _retrievalState = MutableStateFlow<RetrievalState>(RetrievalState.Loading)
    val retrievalState: StateFlow<RetrievalState> = _retrievalState

    fun fetchUsers() {
        _retrievalState.value = RetrievalState.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val users = NetworkService.userListService.getUsers()
                    val userList = mutableListOf<User>()
                    users.forEach {
                        userList.add(it.toUser())
                    }
                    _retrievalState.value = RetrievalState.Success(userList)
                }
                catch (exc: Exception) {
                    _retrievalState.value = RetrievalState.Error(exc.message.orEmpty())
                    exc.printStackTrace()
                }
            }
        }
    }
}