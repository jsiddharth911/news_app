package com.example.news.presentation.commonScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.news.data.database.entity.ArticleEntity

@Composable
fun <T> NewsCard(
    news: T,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(2.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(modifier = Modifier.background(Color.White)){
            // Extracting common properties
            val urlToImage: String? = news.extractUrlToImage()
            val title: String? = news.extractTitle()
            val description: String? = news.extractDescription()
            val author: String? = news.extractAuthor()

            if (urlToImage != null) {
                Image(
                    painter = rememberAsyncImagePainter(urlToImage),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )
            }

            title?.let {
                Text(
                    text = it,
                    style = TextStyle(fontSize = 15.sp, fontFamily = FontFamily.Serif),
                    modifier = Modifier.padding(5.dp),
                    maxLines = 1
                )
            }
            description?.let {
                Text(
                    text = it,
                    style = TextStyle(fontSize = 10.sp, fontFamily = FontFamily.Monospace),
                    modifier = Modifier.padding(5.dp),
                    maxLines = 3
                )
            }

            author?.let {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(vertical = 8.dp)
                )
                Row {
                    Text(
                        text = "Author: $it",
                        style = TextStyle(fontSize = 10.sp, fontFamily = FontFamily.Monospace),
                        modifier = Modifier.padding(5.dp, bottom = 10.dp)
                    )
                }
            }
        }
    }
}

// Extensions to extract common properties from different types of news
private fun Any?.extractUrlToImage(): String? {
    // Implement the logic to extract urlToImage from any type of news
    return when (this) {
        is ArticleEntity -> this.urlToImage
        // Add cases for other types of news
        else -> null
    }
}

private fun Any?.extractTitle(): String? {
    // Implement the logic to extract title from any type of news
    return when (this) {
        is ArticleEntity -> this.title
        // Add cases for other types of news
        else -> null
    }
}

private fun Any?.extractDescription(): String? {
    // Implement the logic to extract description from any type of news
    return when (this) {
        is ArticleEntity -> this.description
        // Add cases for other types of news
        else -> null
    }
}

private fun Any?.extractAuthor(): String? {
    // Implement the logic to extract author from any type of news
    return when (this) {
        is ArticleEntity -> this.author
        // Add cases for other types of news
        else -> null
    }
}