package com.example.news.domain

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.news.R
import com.example.news.data.repository.NewsRepository
import com.example.news.utils.Results
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch

@HiltWorker
class NewsWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val newsRepository = ServiceLocator.newsRepository
        Log.d("worker call", "this is a worker call")
        var result: Result? = null
        try {
            newsRepository.workManagerRequest()
                .catch {
                    result = Result.failure()
                    Log.d("worker call", "this is a worker call failure from 1st catch")
                }
                .collect { flowResult ->
                    when (flowResult) {
                        is Results.Success -> {
                            Log.d("worker call", "this is a worker call Success")
                            // Handle the successful case
                            // You can process the PagingData here
                            showNotification()
                        }
                        is Results.Error -> {
                            Log.d("worker call", "this is a worker call failure from Result.Error")
                            // Handle the error case
                            result = Result.failure()
                        }
                    }
                    if (result != null) return@collect
                }
        } catch (e: Exception) {
            Log.d("worker call", "this is a worker call failure from second catch :$e")
            result = Result.failure()
        }
        return result ?: Result.success()
    }

    private fun showNotification() {
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_NOTIFICATION_POLICY
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is granted, proceed with creating the channel
                val channel = NotificationChannel(
                    "News Channel ID",
                    "News Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            } else {
                // Permission is not granted, show a dialog to ask for permission
                return
            }
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, "News Channel ID")
            .setContentTitle("News")
            .setContentText("You have new updated news please check it out")
            .setSmallIcon(R.drawable.news)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(1, notificationBuilder.build())
    }
}

object ServiceLocator {
    lateinit var newsRepository: NewsRepository
}