package com.prafull.chatminds.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
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
fun Main(navController: NavHostController, updateScreen: (String) -> Unit) {

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
        composable(Screens.Chat.name + "/{model}" + "/{prompt}") {
            val viewModel: NewChatViewModel = hiltViewModel()
            updateScreen(Screens.Chat.name)
            ChatScreen(navController, viewModel)
        }
    }
}