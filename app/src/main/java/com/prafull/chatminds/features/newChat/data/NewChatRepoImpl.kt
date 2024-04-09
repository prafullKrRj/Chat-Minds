package com.prafull.chatminds.features.newChat.data

import com.prafull.chatminds.core.Resource
import com.prafull.chatminds.features.newChat.domain.NewChatRepo
import dagger.Component
import kotlinx.coroutines.flow.Flow
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
    override suspend fun getChatResponse(model: String): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading)
            emit(Resource.Success("Hello, I am GPT-3.5"))
        }
    }

}