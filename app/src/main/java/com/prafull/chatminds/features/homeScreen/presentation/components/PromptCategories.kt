package com.prafull.chatminds.features.homeScreen.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/***
 *
 *  PromptCategories is a composable function that displays the categories of prompts.
 */

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

