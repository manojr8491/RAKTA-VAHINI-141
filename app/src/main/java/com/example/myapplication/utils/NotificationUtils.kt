package com.example.myapplication.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.myapplication.R

object NotificationUtils {
    private const val CHANNEL_ID = "emergency_sos_channel"

    fun showEmergencyNotification(context: Context, hospitalName: String, bloodGroup: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Emergency SOS Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Critical alerts for blood requests"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(androidx.core.R.drawable.notification_bg)
            .setContentTitle("CRITICAL SOS: $bloodGroup Needed")
            .setContentText("Immediate assistance required at $hospitalName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000))
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
