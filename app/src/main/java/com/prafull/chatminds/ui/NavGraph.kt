package com.prafull.chatminds.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prafull.chatminds.chatScreen.ui.ChatScreen
import com.prafull.chatminds.features.history.presentation.HistoryScreen
import com.prafull.chatminds.features.models.presentation.ModelsScreen
import com.prafull.chatminds.features.homeScreen.presentation.HomeScreen
import com.prafull.chatminds.chatScreen.ui.NewChatViewModel
import com.prafull.chatminds.features.profile.presentation.ProfileScreen
import com.prafull.chatminds.features.settings.presentation.SettingsScreen
import org.koin.androidx.compose.getViewModel


@Composable
fun Main(navController: NavHostController, updateScreen: (String) -> Unit) {
    NavHost(navController = navController, startDestination = Screens.NewChat.name) {
        composable(Screens.NewChat.name) {
            updateScreen(Screens.NewChat.name)
            HomeScreen(navController)
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
            val viewModel: NewChatViewModel = getViewModel<NewChatViewModel>()
            updateScreen(Screens.Chat.name)
            ChatScreen(navController, viewModel)
        }
    }
}