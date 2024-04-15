package com.prafull.chatminds.features.newChat.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.chatminds.components.PromptField
import com.prafull.chatminds.features.newChat.presentation.components.AdWindow
import com.prafull.chatminds.features.newChat.presentation.components.PremiumPlanComp
import com.prafull.chatminds.features.newChat.presentation.components.PromptCategories
import com.prafull.chatminds.ui.Screens

@Composable
fun NewChatScreen(navController: NavController) {
    var selectModel by remember {
        mutableStateOf(false)
    }
    var selectedModel by rememberSaveable {
        mutableStateOf("GPT-3.5")
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            PremiumPlanComp()
            AdWindow()
        }
        item {
            PromptCategories()
        }
        item {
            PromptField {
                selectModel = true
            }
        }
    }
    if (selectModel) {
        AlertDialog(
            onDismissRequest = {
                selectModel = false
            },
            title = {
                Text(text = "Select Model")
            },
            text = {
                Column {
                    Text(text = "Select the model you want to use for generating text.")
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn {
                        items(listOf("GPT-4.5", "GPT-4.0", "GPT-3.5", "Gemini-Pro")) { model ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedModel = model
                                    }
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(checked = selectedModel == model, onCheckedChange = {
                                    selectModel = it
                                    selectedModel = model
                                })
                                Text(text = model)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectModel = false
                        navController.navigate(Screens.Chat.name + "/$selectedModel")
                    }
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectModel = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}

