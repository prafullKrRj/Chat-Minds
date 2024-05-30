package com.prafull.chatminds.chatScreen.data

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.prafull.chatminds.chatScreen.domain.NewChatRepo
import com.prafull.chatminds.chatScreen.model.Chat
import com.prafull.chatminds.chatScreen.model.ChatMessage
import com.prafull.chatminds.chatScreen.model.Role
import com.prafull.chatminds.commons.core.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

sealed class Models {
    object OpenAI : Models()
    object Custom : Models()
}
class NewChatRepoImpl : NewChatRepo {


    override suspend fun getChatResponse(chat: Chat, message: String): Flow<Resource<ChatMessage>> {
        return callbackFlow {
            trySend(Resource.Loading)
            if (chat.model == Models.OpenAI) {
                trySend(
                    Resource.Success(
                        OpenAiClient.fromOpenAI(
                            message = message,
                            model = "gpt-3.5-turbo",
                        )
                    )
                )
            } else {
                trySend(Resource.Success(
                    ChatMessage(
                        message = message,
                        role = Role.BOT,
                        success = true
                    )
                ))
            }
            awaitClose {  }
        }
    }
    override suspend fun addToDb(chat: Chat) {

    }

}
object OpenAiClient {
    private val openAi by lazy {
        OpenAI(
            config = OpenAIConfig(
                    token ="sk-proj-Pjmt1NkfSonHEcKQhTIAT3BlbkFJD2f3HJTolIQgFQh5h7OF"
            )
        )
    }
    private var chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            messages = listOf(
                    com.aallam.openai.api.chat.ChatMessage(
                            role = ChatRole.System,
                            content = "You are a helpful assistant!"
                    ),
                    com.aallam.openai.api.chat.ChatMessage(
                            role = ChatRole.User,
                            content = "Hello!"
                    )
            )
    )
    suspend fun fromOpenAI(message: String, model: String): ChatMessage {
        chatCompletionRequest = ChatCompletionRequest(
                model = ModelId(model),
                messages = chatCompletionRequest.messages.toMutableList().apply {
                    add(
                        com.aallam.openai.api.chat.ChatMessage(
                                role = ChatRole.User,
                                content = message
                        )
                    )
                }
        )
        return openAi.chatCompletion(chatCompletionRequest).choices.first().message.toGenericChatMessage()
    }
}
fun com.aallam.openai.api.chat.ChatMessage.toGenericChatMessage(): ChatMessage {
    return ChatMessage(
            message = content?: "Sorry, I didn't get that",
            role = when (role) {
                ChatRole.User -> Role.USER
                ChatRole.System -> Role.BOT
                else -> {
                    Role.BOT
                }
            },
            success = true
    )
}