package com.prafull.llm_client.data

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.prafull.llm_client.Responses
import com.prafull.llm_client.models.GenericChatMessage
import com.prafull.llm_client.models.Role


object OpenAIClient {
    private val openAI = OpenAI(
            config = OpenAIConfig(
                    token = "sk-proj-Pjmt1NkfSonHEcKQhTIAT3BlbkFJD2f3HJTolIQgFQh5h7OF"
            )
    )
    private val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            messages = listOf(
                    ChatMessage(
                            role = ChatRole.System,
                            content = "You are a helpful assistant!"
                    ),
                    ChatMessage(
                            role = ChatRole.User,
                            content = "Hello!"
                    )
            )
    )
    suspend fun getChatResponse(model: String, currChat: List<GenericChatMessage>): Responses<GenericChatMessage> {
        val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId(model),
                messages = currChat.map {
                    it.toChatMessage()
                }
        )
        try {
            val response = openAI.chatCompletion(chatCompletionRequest)
            return Responses.Success(
                    GenericChatMessage(
                    role = Role.BOT,
                    message = response.choices.first().message.content,
                    isSuccess = true
            )
            )
        } catch (e: Exception) {
            return Responses.Error(
                    GenericChatMessage(
                    role = Role.BOT,
                    message = e.message
            )
            )
        }
    }
}