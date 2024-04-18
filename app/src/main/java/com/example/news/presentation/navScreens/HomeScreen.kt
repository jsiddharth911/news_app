package com.example.news.presentation.navScreens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.news.presentation.NewsViewModel
import com.example.news.presentation.commonScreens.NewsCard
import com.example.news.presentation.commonScreens.alertDialog
import com.example.news.utils.ViewState
import com.example.news.utils.areNotificationsEnabled

@Composable
fun HomeScreen(context: Context) {
    val snackbarHostState = remember { SnackbarHostState() }
    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Check if notifications are enabled
        if (!areNotificationsEnabled(context = context)) {
            // Notifications are disabled, show the permission dialog
            showDialog.value = true
        }
    }
    if(!showDialog.value) {
        NewsListScreen(snackbarHostState = snackbarHostState)
    } else {
        CallDialog(showDialog = showDialog, context = context)
    }
}

@Composable
private fun CallDialog(showDialog: MutableState<Boolean>, context: Context) {
    alertDialog(
        onEnableClick = {
            handleEnableClick(context = context)
            showDialog.value = false
        },
        onCancelClick = {
            showDialog.value = false
        },
        tittle = "Enable Notifications",
        description = "Notifications are disabled for this app. Enable them to receive news updates."
    )
}


private fun handleEnableClick(context: Context) {
    val intent = Intent()
    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
    intent.putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
    context.startActivity(intent)
}

@Composable
fun NewsListScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (viewState) {
            is ViewState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is ViewState.Success -> {
                val newsPagingItems = viewModel.newsDataFlow.collectAsLazyPagingItems()

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(
                        count = newsPagingItems.itemCount,
                        key = newsPagingItems.itemKey { it.id },
                    ) { index ->
                        val newsArticle = newsPagingItems[index]
                        if (newsArticle != null) {
                            NewsCard(news = newsArticle, onClick = {},
                                modifier = Modifier.fillMaxWidth())
                        }
                    }
                    newsPagingItems.loadState.apply {
                        when {
                            append is LoadState.Loading -> {
                                item {
                                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                                }
                            }
                        }
                    }
                }
            }
            is ViewState.Error -> {
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar(
                        (viewState as ViewState.Error).message
                    )
                }
            }

            ViewState.Idle -> TODO()
        }
    }
}
/*
@Composable
fun NewsCard(news: ArticleEntity, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(2.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(modifier = Modifier.background(Color.White)){
            Image(
                painter = rememberAsyncImagePainter(news.urlToImage),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            if(news.title != null) {
                Text(text = news.title,
                    style = TextStyle(fontSize = 15.sp,
                        fontFamily = FontFamily.Serif),
                    modifier = Modifier.padding(5.dp),
                    maxLines = 1)
            }
            if(news.description != null) {
                Text(text = news.description,
                    style = TextStyle(fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace),
                    modifier = Modifier.padding(5.dp),
                    maxLines = 3)
            }

            if(news.author != null) {
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(vertical = 8.dp))
                Row {
                    Text(text = "Author: ${news.author}",
                        style = TextStyle(fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace),
                        modifier = Modifier.padding(5.dp, bottom = 10.dp))
                }
            }
        }
    }
}

 */


