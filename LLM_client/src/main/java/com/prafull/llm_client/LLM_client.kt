package com.prafull.llm_client

import com.prafull.llm_client.data.OpenAIClient
import com.prafull.llm_client.models.GenericChatMessage
import com.prafull.llm_client.models.Role

class Client {
    private var currChat: List<GenericChatMessage> = emptyList()
    suspend fun getTextResponse(model: Models, message: String) : GenericChatMessage {
        currChat = currChat + GenericChatMessage(role = Role.USER, message = message)
        println(model)
        when (model) {
            Models.Anthropic -> {
                return GenericChatMessage(
                        role = Role.BOT,
                        message = "Anthropic model not supported yet"
                )
            }
            Models.Gemini -> {
                return GenericChatMessage(
                        role = Role.BOT,
                        message = "Gemini model not supported yet"
                )
            }
            Models.OpenAI -> {
               /* val response = OpenAIClient.getChatResponse(
                        currChat = currChat,
                        model = Models.OpenAI.gpt_3_5_Turbo
                )
                println(response)
                if (response is Responses.Success) {
                    currChat.apply {
                        this + response.data
                    }
                    return response.data
                }
                if (response is Responses.Error) {
                    currChat.apply {
                        this + response.exception
                    }
                    return response.exception
                }*/
                return GenericChatMessage(
                        role = Role.BOT,
                        message = "Gemini model not supported yet",
                        isSuccess = true
                )
            }
        }
        return GenericChatMessage(
            role = Role.BOT,
            message = "Error"
        )
    }
}
class ClaudeClient {

}
sealed interface Models {
    data object OpenAI : Models {
        const val gpt_3_5_Turbo = "gpt-3.5-turbo"
    }
    data object Anthropic : Models {
        const val claude = "claude"
    }
    data object Gemini : Models {
        const val geminiPro = "gemini-pro"
    }
}
sealed interface Responses<out T> {
    data class Success<out T>(val data: T) : Responses<T>
    data class Error<out T>(val exception: T) : Responses<T>
}