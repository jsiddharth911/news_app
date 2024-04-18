package com.example.news.utils

import android.app.NotificationManager
import android.content.Context

fun areNotificationsEnabled(context: Context): Boolean {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    return notificationManager.areNotificationsEnabled()
}