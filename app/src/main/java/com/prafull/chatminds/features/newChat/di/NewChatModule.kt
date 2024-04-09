package com.prafull.chatminds.features.newChat.di

import androidx.compose.runtime.sourceInformation
import com.prafull.chatminds.features.newChat.data.NewChatRepoImpl
import com.prafull.chatminds.features.newChat.domain.NewChatRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.net.PasswordAuthentication
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewChatModule {

    @Provides
    @Singleton
    @Named("API_KEY")
    fun providesApiKey(): String = "API_KEY"
    @Provides
    @Singleton
    fun providesNewChatRepo(@Named("API_KEY") apiKey: String): NewChatRepo {
        return NewChatRepoImpl(apiKey)
    }
}