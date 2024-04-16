package com.prafull.chatminds.features.onBoardAuth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.prafull.chatminds.ui.MajorScreens

@Composable
fun AuthScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {
    Column {
        Button(onClick = {
            navController.navigate(MajorScreens.Main.name)
        }) {
            Text(text = "Login")
        }
    }
}