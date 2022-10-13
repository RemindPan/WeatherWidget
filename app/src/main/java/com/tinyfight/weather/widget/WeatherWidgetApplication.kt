package com.tinyfight.weather.widget

import android.app.Application
import com.tinyfight.weather.widget.receiver.IntervalTimeReceiver

class WeatherWidgetApplication : Application() {
    lateinit var receiver: IntervalTimeReceiver

    override fun onCreate() {
        super.onCreate()
        application = this
        receiver = IntervalTimeReceiver()
        registerReceiver(receiver, IntervalTimeReceiver.intentFilter())
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(receiver)
    }

    companion object {
        lateinit var application: Application
    }
}