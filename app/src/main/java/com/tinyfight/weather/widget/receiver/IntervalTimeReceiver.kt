package com.tinyfight.weather.widget.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.tinyfight.weather.widget.XINBEI_LOCATION_ID
import com.tinyfight.weather.widget.network.WeatherApi
import com.tinyfight.weather.widget.updateWidget
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IntervalTimeReceiver : BroadcastReceiver() {
    private var timeCount = 0

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_TIME_TICK
            || intent?.action == Intent.ACTION_TIME_CHANGED
        ) {
            Log.d("IntervalTimeReceiver", "get broadcast")
            if (timeCount++ < 5) {
                return
            } else {
                timeCount = 0
            }
            context?.let { ctx ->
                GlobalScope.launch {
                    Log.d("IntervalTimeReceiver", "already 5 min")
                    try {
                        val result = WeatherApi.api.getNowWeather(locationId = XINBEI_LOCATION_ID)
                        if (result.code == "200") {
                            updateWidget(ctx, result.now.text, result.now.temp)
                        }
                    } catch (ex: Exception) {
                        //
                    }
                }
            }

        }
    }


    companion object {
        fun intentFilter(): IntentFilter {
            return IntentFilter().apply {
                addAction(Intent.ACTION_TIME_TICK)
                addAction(Intent.ACTION_TIME_CHANGED)
            }
        }
    }
}