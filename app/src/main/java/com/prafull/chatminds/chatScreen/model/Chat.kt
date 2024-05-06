package com.prafull.chatminds.chatScreen.model

import java.util.Date

data class Chat(
    val createdTime: Long = Date().time,
    val lastModified: Long = Date().time,
    val model: String = "GPT-3.5",
    val messages: List<ChatMessage> = emptyList()
) {

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