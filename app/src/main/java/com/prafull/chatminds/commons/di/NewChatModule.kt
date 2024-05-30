package com.prafull.chatminds.commons.di

import com.prafull.chatminds.chatScreen.data.NewChatRepoImpl
import com.prafull.chatminds.chatScreen.domain.NewChatRepo
import com.prafull.chatminds.chatScreen.ui.NewChatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val newChatModule = module {
    single<NewChatRepo> {
        NewChatRepoImpl()
    }
    single {

    }
    viewModel {
        NewChatViewModel(newChatRepo = get(), savedStateHandle = get())
    }
}