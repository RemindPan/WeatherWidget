package com.tinyfight.weather.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews

fun updateWidget(context: Context, weather: String, temp: String) {
    val remoteView = RemoteViews(context.packageName, R.layout.view_remote_widget)
    remoteView.setTextViewText(R.id.city, "常州")
    remoteView.setTextViewText(R.id.weather, weather)
    remoteView.setTextViewText(R.id.temp, "$temp°C")


    val weatherIntent = Intent(context, MainActivity::class.java)
    weatherIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    var flag = PendingIntent.FLAG_UPDATE_CURRENT
    if (Build.VERSION.SDK_INT >= 31) {
        flag = flag or PendingIntent.FLAG_MUTABLE
    }
    val weatherPI = PendingIntent.getActivity(context, 0, weatherIntent, flag)
    remoteView.setOnClickPendingIntent(R.id.container, weatherPI)

    val componentName = ComponentName(context, WeatherAppWidgetProvider::class.java)
    AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteView)
}