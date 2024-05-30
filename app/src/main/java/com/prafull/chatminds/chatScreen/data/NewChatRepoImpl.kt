package com.prafull.chatminds.chatScreen.data

import com.prafull.chatminds.chatScreen.model.Chat
import com.prafull.chatminds.chatScreen.model.ChatMessage
import com.prafull.chatminds.chatScreen.model.Role
import com.prafull.chatminds.commons.core.Resource
import com.prafull.chatminds.chatScreen.domain.NewChatRepo
import com.prafull.llm_client.Client
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Named


class NewChatRepoImpl(

): NewChatRepo {
    private val llmClient = Client()
    override suspend fun getChatResponse(chat: Chat, message: String): Flow<Resource<ChatMessage>> {

        return callbackFlow {
            trySend(Resource.Loading)
            delay(1000)
            try {
                val response = llmClient.getTextResponse(
                        model = chat.model,
                        message = message
                )
                if (response.isSuccess) {
                    trySend(Resource.Success(
                        ChatMessage(
                            role = Role.BOT,
                            message = response.message ?: "Error",
                        )
                    ))
                } else {
                    trySend(Resource.Error(
                        ChatMessage(
                            role = Role.BOT,
                            message = response.message ?: "Error",
                        )
                    ))
                }
            } catch (e: Exception) {
                trySend(Resource.Error(
                    ChatMessage(
                        role = Role.BOT,
                        message = "${e.message}",
                    )
                ))
            }
            awaitClose {  }
        }
    }
    override suspend fun addToDb(chat: Chat) {

    }

}