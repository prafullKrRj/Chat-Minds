package com.prafull.chatminds.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prafull.chatminds.chatScreen.ChatScreen
import com.prafull.chatminds.features.history.presentation.HistoryScreen
import com.prafull.chatminds.features.models.presentation.ModelsScreen
import com.prafull.chatminds.features.newChat.presentation.NewChatScreen
import com.prafull.chatminds.features.newChat.presentation.NewChatViewModel
import com.prafull.chatminds.features.profile.presentation.ProfileScreen
import com.prafull.chatminds.features.settings.presentation.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController, updateScreen: (String) -> Unit) {
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    NavHost(navController = navController, startDestination = Screens.NewChat.name) {
        composable(Screens.NewChat.name) {
            updateScreen(Screens.NewChat.name)
            NewChatScreen(navController)
        }
        composable(Screens.History.name) {
            updateScreen(Screens.History.name)
            HistoryScreen()
        }
        composable(Screens.Profile.name) {
            updateScreen(Screens.Profile.name)
            ProfileScreen()
        }
        composable(Screens.Models.name) {
            updateScreen(Screens.Models.name)
            ModelsScreen()
        }
        composable(Screens.Settings.name) {
            updateScreen(Screens.Settings.name)
            SettingsScreen()
        }
        composable(Screens.Chat.name + "/{model}") {
            val model = it.arguments?.getString("model") ?: "GPT-4.5"
            val viewModel: NewChatViewModel = viewModel(viewModelStoreOwner)
            viewModel.model = model
            updateScreen(Screens.Chat.name)
            ChatScreen(navController, viewModel)
        }
    }
}