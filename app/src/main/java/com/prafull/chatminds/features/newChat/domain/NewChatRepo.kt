package com.prafull.chatminds.features.newChat.domain

import com.prafull.chatminds.core.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
interface NewChatRepo {

    suspend fun hasPremiumAccess(): Flow<Boolean>
    suspend fun getChatResponse(model: String): Flow<Resource<String>>
}