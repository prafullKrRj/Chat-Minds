package com.prafull.chatminds.features.newChat.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.chatminds.chatScreen.model.Chat
import com.prafull.chatminds.chatScreen.model.ChatMessage
import com.prafull.chatminds.chatScreen.model.Role
import com.prafull.chatminds.core.Resource
import com.prafull.chatminds.features.newChat.domain.NewChatRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewChatViewModel @Inject constructor(
    private val newChatRepo: NewChatRepo,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var model by mutableStateOf(savedStateHandle.get<String>("model") ?: "GPT-3.5")

    private val _chat = MutableStateFlow(UiState(
        model = model
    ))
    val chat = _chat.asStateFlow()
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()
    init {
        sendMessage("Hello")
    }
    fun sendMessage(message: String) {
        viewModelScope.launch {
            _chat.update {
                it.copy(
                    chat = it.chat + ChatMessage(
                        message = message,
                        role = Role.USER
                    )
                )
            }
            newChatRepo.getChatResponse(Chat(model = model, messages = _chat.value.chat), message).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _chat.update {
                            it.copy(
                                chat = it.chat + response.data
                            )
                        }
                        _isLoading.update { false }
                    }
                    is Resource.Error -> {
                        _chat.update {
                            it.copy(
                                chat = it.chat + ChatMessage(
                                    message = "Error: ${it}",
                                )
                            )
                        }
                        _isLoading.update { false }
                    }
                    is Resource.Loading -> {
                        _isLoading.update {
                            true
                        }
                    }
                }
            }
        }
    }
}

data class UiState(
    val model: String = "GPT-3.5",
    val chat: List<ChatMessage> = emptyList()
)