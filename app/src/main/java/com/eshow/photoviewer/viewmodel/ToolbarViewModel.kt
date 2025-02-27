package com.eshow.photoviewer.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ToolbarViewModel {
    private val _showBackState = MutableStateFlow(false)
    val showBackState: StateFlow<Boolean> = _showBackState

    private val _titleState = MutableStateFlow("")
    val titleState: StateFlow<String> = _titleState

    fun setTitle(title: String) {
        _titleState.value = title
    }

    fun setShowBack(showBack: Boolean) {
        _showBackState.value = showBack
    }
}