package com.prafull.chatminds.newChat.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prafull.chatminds.ui.theme.gold

@Composable
fun NewChatScreen() {
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
            PromptField()
        }
    }
}

@Composable
fun PromptCategories() {
    val list = listOf(
        "Generate Image" to "Generate image with text, image, and background.",
        "Business" to "AI writing with advanced input personalized, high quality content creation.",
        "Interview" to "Dialog with AI to prepare for interviews, get feedback, and improve your skills.",
        "Career" to "Dialog with AI to get career advice, job search tips, and professional development.",
        "Email Writer" to "Dialog with AI to write professional emails, improve your communication skills.",
        "Article Writer" to "Dialog with AI to write articles, blog posts, and other content.",
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(list) {
            Card(
                modifier = Modifier
                    .padding(end = 8.dp, start = if (it == list.first()) 16.dp else 0.dp)
                    .width(200.dp)
                    .height(200.dp)
                    .clickable {

                    },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
            ) {
                Text(
                    it.first,
                    Modifier
                        .padding(top = 12.dp)
                        .padding(horizontal = 12.dp),
                    fontSize = 20.sp, fontWeight = FontWeight.Bold
                )
                Text(text = it.second, modifier = Modifier.padding(horizontal = 12.dp))
            }
        }
    }
}
@Composable
fun AdWindow() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Column(
                modifier = Modifier
                    .weight(.8f)
            ) {
                Text(text = "Watch Video Ad", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Watch a video ad to earn 5000 tokens!")
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = gold),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Watch Video Ad")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Watch Ad", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
            Box(modifier = Modifier.weight(.2f))
        }

    }
}
@Composable
fun PromptField(modifier: Modifier = Modifier, sendPrompt: (String) -> Unit = {}) {
    Box(modifier = modifier.padding(8.dp)) {
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue("", TextRange(0, 7)))
        }
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
                    sendPrompt(text.text)
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

/**
 *  Premium Plan Component
 * */
@Composable
fun PremiumPlanComp(modifier: Modifier = Modifier, onPremiumButtonClick: () -> Unit = {}) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Row(Modifier.padding(16.dp)) {
            Column(Modifier.weight(.8f)) {
                Text(text = "Premium Plan", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = "Harness the full power of ai with our premium plan!")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onPremiumButtonClick, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background)) {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Premium Plan", tint = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Upgrade to Premium", color = MaterialTheme.colorScheme.onBackground)
                }
            }
            Box(modifier = Modifier.weight(.2f))
        }
    }
}