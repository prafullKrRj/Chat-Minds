package com.prafull.chatminds.features.newChat.data

import com.prafull.chatminds.chatScreen.model.Chat
import com.prafull.chatminds.chatScreen.model.ChatMessage
import com.prafull.chatminds.chatScreen.model.Role
import com.prafull.chatminds.core.Resource
import com.prafull.chatminds.features.newChat.domain.NewChatRepo
import dagger.Component
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named


class NewChatRepoImpl @Inject constructor(
    @Named("API_KEY") private val  apiKey: String
): NewChatRepo {
    override suspend fun hasPremiumAccess(): Flow<Boolean> {
        return flow {
            emit(true)
        }
    }

    override suspend fun getChatResponse(chat: Chat, message: String): Flow<Resource<ChatMessage>> {
        return callbackFlow {
            trySend(Resource.Loading)
            delay(2000)
            trySend(
                Resource.Success(
                    ChatMessage(
                        message = "Hey Hey",
                        role = Role.BOT
                    )
                )
            )
            awaitClose {  }
        }
    }
}