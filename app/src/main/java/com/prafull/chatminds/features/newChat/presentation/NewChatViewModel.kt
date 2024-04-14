package com.prafull.chatminds.features.newChat.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prafull.chatminds.features.newChat.domain.NewChatRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewChatViewModel @Inject constructor(
    private val newChatRepo: NewChatRepo,
): ViewModel() {

    var model by mutableStateOf("GPT-4.5")
    init {
        Log.d("major", "NewChatViewModel $model" )
        viewModelScope.launch {
            newChatRepo.hasPremiumAccess()
        }
    }
}