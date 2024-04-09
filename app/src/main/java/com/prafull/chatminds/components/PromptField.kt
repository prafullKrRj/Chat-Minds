package com.prafull.chatminds.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PromptField(modifier: Modifier = Modifier, sendPrompt: (String) -> Unit = {}) {
    Box(modifier = modifier.padding(8.dp)) {
        var text by rememberSaveable { mutableStateOf("") }
        TextField(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            value = text,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            onValueChange = { text = it },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotEmpty()) {
                        sendPrompt(text)
                        text = ""
                    }
                }) {
                    Icon(imageVector = Icons.Outlined.Send, contentDescription = "Send Prompt")
                }
            },
            label = {
                Text(text = "Prompt")
            }
        )
    }
}
