package com.prafull.chatminds.chatScreen.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafull.chatminds.chatScreen.ui.components.ChatExitDialog
import com.prafull.chatminds.chatScreen.ui.components.TopAppBar
import com.prafull.chatminds.chatScreen.model.ChatMessage
import com.prafull.chatminds.chatScreen.model.Role
import com.prafull.chatminds.commons.components.PromptField

@Composable
fun ChatScreen(navController: NavController, newChatViewModel: NewChatViewModel) {
    var alertDialogState by remember { mutableStateOf(false) }
    BackHandler {   // handle back press
        if (!alertDialogState) {
            alertDialogState = true
        }
    }
    if (alertDialogState) {
        // show dialog on back press
        ChatExitDialog(navController) {
            alertDialogState = it
        }
    }
    val chat by newChatViewModel.chat.collectAsState() // get the chat
    val isLoading by newChatViewModel.isLoading.collectAsState() // check if the chat is loading
    val listState = rememberLazyListState()
    LaunchedEffect(key1 = chat.chat.messages.size) {
        listState.animateScrollToItem(chat.chat.messages.size - 1)
    }
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
            contentPadding = PaddingValues(
                bottom = paddingValues.calculateBottomPadding(),
                top = paddingValues.calculateTopPadding(),
                start = 12.dp,
                end = 12.dp
            ),
            state = listState,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(chat.chat.messages) { message ->
                ChatMessageComposable(chatMessage = message, onRetry = newChatViewModel::retry)
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                if (isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
@Composable
fun ChatMessageComposable(chatMessage: ChatMessage, onRetry: () -> Unit) {
    if (chatMessage.role == Role.BOT && chatMessage.success) {
        Text(text = "Model", fontSize = 12.sp)
        Text(text = chatMessage.message)
    } else if (chatMessage.role == Role.USER){
        Text(text = "You", fontSize = 12.sp)
        Text(text = chatMessage.message)
    } else {
        Text(text = "Error", fontSize = 12.sp)
        Text(text = chatMessage.message)
        Row(Modifier.fillMaxWidth()) {
            Button(onClick = onRetry) {
                Text(text = "Retry")
            }
        }
    }
}