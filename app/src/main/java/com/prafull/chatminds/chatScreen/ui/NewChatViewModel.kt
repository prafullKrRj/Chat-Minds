package com.prafull.chatminds.chatScreen.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.chatminds.chatScreen.model.Chat
import com.prafull.chatminds.chatScreen.model.ChatMessage
import com.prafull.chatminds.chatScreen.model.Role
import com.prafull.chatminds.commons.core.Resource
import com.prafull.chatminds.chatScreen.domain.NewChatRepo
import com.prafull.llm_client.Models
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewChatViewModel(
    private val newChatRepo: NewChatRepo,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    var model by mutableStateOf(savedStateHandle.get<String>("model") ?: "GPT-3.5")

    private val _chat = MutableStateFlow(
        UiState(
            model = model
        )
    )
    val chat = _chat.asStateFlow()
    private var lastUserPrompt by mutableStateOf("")
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    init {
        sendMessage(savedStateHandle.get<String>("prompt") ?: "Hey")
    }
    fun sendMessage(message: String) {
        lastUserPrompt = message
        viewModelScope.launch {
            // Add the user message to the chat
            _chat.update {
                it.copy(
                    chat = it.chat.copy(
                        messages = it.chat.messages + ChatMessage(
                            role = Role.USER,
                            message = message
                        )
                    )
                )
            }
            /**
             *      Call the getChatResponse method from the NewChatRepo
             * */
            newChatRepo.getChatResponse(_chat.value.chat, message).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _chat.update {
                            it.copy(
                                chat = it.chat.copy(
                                    messages = it.chat.messages + ChatMessage(
                                        role = Role.BOT,
                                        message = response.data.message
                                    )
                                )
                            )
                        }
                        _isLoading.update { false } // Set the loading state to false
                    }
                    is Resource.Error -> {
                        _chat.update {
                            it.copy(
                                chat = it.chat.copy(
                                    messages = it.chat.messages + ChatMessage(
                                        role = Role.BOT,
                                        message = "Sorry, I am unable to process your request at the moment."
                                    )
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
            newChatRepo.addToDb(chat = _chat.value.chat) // Add the chat to the database
        }
    }
    fun retry() {
        sendMessage(lastUserPrompt)     // Retry the last user prompt
    }
}

data class UiState(
    val model: String = "GPT-3.5",
    val chat: Chat = Chat(
            model = Models.OpenAI
    )
)