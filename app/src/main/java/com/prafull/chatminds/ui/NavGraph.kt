package com.prafull.chatminds.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prafull.chatminds.history.presentation.HistoryScreen
import com.prafull.chatminds.models.presentation.ModelsScreen
import com.prafull.chatminds.newChat.presentation.NewChatScreen
import com.prafull.chatminds.profile.presentation.ProfileScreen
import com.prafull.chatminds.settings.presentation.SettingsScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.NewChat.name) {
        composable(Screens.NewChat.name) {
            NewChatScreen()
        }
        composable(Screens.History.name) {
            HistoryScreen()
        }
        composable(Screens.Profile.name) {
            ProfileScreen()
        }
        composable(Screens.Models.name) {
            ModelsScreen()
        }
        composable(Screens.Settings.name) {
            SettingsScreen()
        }
    }
}