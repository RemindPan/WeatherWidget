package com.tinyfight.weather.widget

import java.util.*

val weekDayList = listOf(
    "周日",
    "周一",
    "周二",
    "周三",
    "周四",
    "周五",
    "周六",
)

fun dayOfWeek(index: Int): String {
    if (index == 0) {
        return "今天"
    }

    val date = Date().apply {
        time += 24 * 3600 * 1000 * index
    }
    val calender = Calendar.getInstance().apply {
        time = date
    }

    return weekDayList[calender.get(Calendar.DAY_OF_WEEK) - 1]
}