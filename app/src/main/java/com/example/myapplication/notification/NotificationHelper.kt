package com.example.myapplication.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.activities.MapsActivity

class NotificationHelper {
    fun sendNotification(body: String?, contextCompats: Context) {
        val intent = Intent(contextCompats, MapsActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra("Notification", body)

        val pendingIntent = PendingIntent.getActivity(contextCompats, 0, intent, PendingIntent.FLAG_ONE_SHOT/*Flag indicating that this PendingIntent can be used only once.*/)
        val notificationManager: NotificationManager = contextCompats.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(contextCompats, "IzhevskOnline")
        } else {
            Notification.Builder(contextCompats)
        }.apply {
            setSmallIcon(R.drawable.ic_logo_foreground)
            setContentTitle("Отличная погода")
            setContentText(body)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
        }.build()
        notificationManager.notify(0, notificationBuilder)
    }

    fun createNotificationChannel(contextCompats: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "IzhevskOnline"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("IzhevskOnline", name, importance)
            val notificationManager: NotificationManager? = ContextCompat.getSystemService(contextCompats, NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }
}