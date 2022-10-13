package com.tinyfight.weather.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import com.tinyfight.weather.widget.network.Result
import com.tinyfight.weather.widget.receiver.IntervalTimeReceiver
import com.tinyfight.weather.widget.repository.WeatherRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class WeatherAppWidgetProvider : AppWidgetProvider() {
    private var receiver: IntervalTimeReceiver? = null
    private val repository: WeatherRepository = WeatherRepository()

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.d("WeatherAppWidgetProvider", "onReceive")
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
//        registerTimeClockReceiver(context)
        updateWidgets(context)
        Log.d("WeatherAppWidgetProvider", "onUpdate")
        for (id in appWidgetIds) {
            Log.d("WeatherAppWidgetProvider", "onUpdate $id")
        }
    }

    override fun onRestored(context: Context?, oldWidgetIds: IntArray?, newWidgetIds: IntArray?) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
//        registerTimeClockReceiver(context)
        Log.d("WeatherAppWidgetProvider", "onRestored")
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
//        registerTimeClockReceiver(context)
        Log.d("WeatherAppWidgetProvider", "onEnabled")
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
//        unRegisterTimeClockReceiver(context)
        Log.d("WeatherAppWidgetProvider", "onDisabled")
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
//        unRegisterTimeClockReceiver(context)
        Log.d("WeatherAppWidgetProvider", "onDeleted")

    }

    private fun registerTimeClockReceiver(context: Context?) {
        if (receiver == null) {
            receiver = IntervalTimeReceiver()
        }

        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
            addAction(Intent.ACTION_TIME_CHANGED)
        }

        context?.apply {
            registerReceiver(receiver, intentFilter)
        }
    }

    private fun unRegisterTimeClockReceiver(context: Context?) {
        context?.apply {
            unregisterReceiver(receiver)
            receiver = null
        }
    }

    private fun updateWidgets(context: Context?) {
        context?.let { ctx ->
            GlobalScope.launch {
                val result = repository.requestNowWeather()
                if (result is Result.Success && result.data.code == "200") {
                    updateWidget(ctx, result.data.now.text, result.data.now.temp)
                }
            }

        }
    }
}