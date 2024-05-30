package com.prafull.llm_client.models

import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole

data class GenericChatMessage(
    val role: Role = Role.USER,
    val message: String? = "",
    val isSuccess: Boolean = false
) {
    fun toChatMessage(): ChatMessage {
        return ChatMessage(
            role = if(role == Role.USER) ChatRole.User else ChatRole.System,
            content = message
        )
    }
}
enum class Role {
    USER,
    BOT
}