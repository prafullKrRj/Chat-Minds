package com.prafull.chatminds.chatScreen.model

import com.prafull.chatminds.features.history.domain.HistoryItem
import com.prafull.chatminds.features.history.domain.HistoryModel
import com.prafull.llm_client.Models
import java.util.Date

data class Chat(
    val createdTime: Long = Date().time,
    val lastModified: Long = Date().time,
    val model: Models,
    val messages: List<ChatMessage> = emptyList()
) {
    fun toHistoryModel(): HistoryModel {
        return HistoryModel(
            chats = listOf(
                HistoryItem(
                    currModel = model,
                    lastModified = lastModified,
                    createdTime = createdTime,
                    chat = messages
                )
            )
        )
    }
}
data class ChatMessage(
    val role: Role = Role.USER,
    val message: String = "",
    val success: Boolean = true
)
enum class Role {
    USER,
    BOT
}