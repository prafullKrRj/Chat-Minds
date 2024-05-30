package com.prafull.chatminds.features.history.domain

import com.prafull.chatminds.chatScreen.model.ChatMessage
import com.prafull.llm_client.Models

data class HistoryModel(
    val chats: List<HistoryItem>,
)
data class HistoryItem(
    val currModel: Models,
    val lastModified: Long,
    val createdTime: Long,
    val chat: List<ChatMessage>
)