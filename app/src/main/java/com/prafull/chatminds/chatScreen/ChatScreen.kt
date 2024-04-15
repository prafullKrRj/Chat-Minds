package com.prafull.chatminds.chatScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.prafull.chatminds.chatScreen.model.Role
import com.prafull.chatminds.components.PromptField
import com.prafull.chatminds.features.newChat.presentation.NewChatViewModel
import com.prafull.chatminds.ui.goBackStack


@Composable
fun ChatScreen(navController: NavController, newChatViewModel: NewChatViewModel) {
    var alertDialogState by remember { mutableStateOf(false) }
    BackHandler {
        if (!alertDialogState) {
            alertDialogState = true
        }
    }
    if (alertDialogState) {
        ChatExitDialog(navController) {
            alertDialogState = it
        }
    }
    val chat by newChatViewModel.chat.collectAsState()
    val isLoading by newChatViewModel.isLoading.collectAsState()

    Scaffold(
        bottomBar = {
            PromptField {
                newChatViewModel.sendMessage(it)
            }
        },
        topBar = {
            TopAppBar(navController = navController, newChatViewModel.model)
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {
            items(chat.chat) {
                Text(text = it.message, color = if (it.role == Role.USER) {
                    androidx.compose.ui.graphics.Color.Blue
                } else {
                    androidx.compose.ui.graphics.Color.Red
                })
            }
            item {
                if (isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavController, model: String) {
    var alertDialogState by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        title = {
                Text(text = model)
        },
        navigationIcon = {
            IconButton(onClick = {
                alertDialogState = true
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
    if (alertDialogState) {
        ChatExitDialog(navController) {
            alertDialogState = it
        }
    }
}

@Composable
fun ChatExitDialog(navController: NavController, onDismissRequest: (Boolean) -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismissRequest(false) },
        title = {
            Text(text = "Exit")
        },
        text = {
            Text(text = "Do you want to exit?")
        },
        confirmButton = {
            TextButton(onClick = {
                onDismissRequest(false)
                navController.goBackStack()
            }) {
                Text(text = "Exit")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest(false) }) {
                Text(text = "Cancel")
            }
        }
    )
}