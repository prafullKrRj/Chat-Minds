package com.prafull.chatminds.chatScreen.domain

import com.prafull.chatminds.chatScreen.model.Chat
import com.prafull.chatminds.chatScreen.model.ChatMessage
import com.prafull.chatminds.commons.core.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
interface NewChatRepo {

    suspend fun getChatResponse(chat: Chat, message: String): Flow<Resource<ChatMessage>>
}