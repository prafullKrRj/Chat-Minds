package com.prafull.chatminds

import android.app.Application
import com.prafull.chatminds.commons.di.newChatModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
class ChatMindsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ChatMindsApplication)
            modules(newChatModule)
        }
    }
}