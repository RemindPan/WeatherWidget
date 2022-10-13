package com.tinyfight.weather.widget.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyfight.weather.widget.XINBEI_LOCATION_ID
import com.tinyfight.weather.widget.model.*
import com.tinyfight.weather.widget.repository.WeatherRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class MainState(
    val isRequesting: Boolean = false,
    val temp: String = "",
    val weather: String = "",
    val hourWeatherList: List<Hourly> = emptyList(),
    val dailyWeatherList: List<Daily> = emptyList()
)

class MainViewModel(private val repository: WeatherRepository) : ViewModel() {
    val mainState = MutableLiveData<MainState>().apply {
        value = MainState()
    }

    fun requestWeather() {
        viewModelScope.launch {
            val nowResult = repository.requestNowWeather(locationId = XINBEI_LOCATION_ID)
            val hourlyResult = repository.requestHourlyWeather(locationId = XINBEI_LOCATION_ID)
            val dailyResult = repository.requestDailyWeather(locationId = XINBEI_LOCATION_ID)

            merge(nowResult, hourlyResult, dailyResult).onStart {
                mainState.value = mainState.value!!.copy(isRequesting = true)
            }.onCompletion {
                mainState.value = mainState.value!!.copy(isRequesting = false)
            }.catch {
                // do nothing, just avoid crash
            }.collect { data ->
                when (data) {
                    is NowWeatherModel -> {
                        mainState.value =
                            mainState.value!!.copy(temp = data.now.temp, weather = data.now.text)
                    }
                    is HourWeatherModel -> {
                        mainState.value = mainState.value!!.copy(hourWeatherList = data.hourly)
                    }
                    is DayWeatherModel -> {
                        mainState.value = mainState.value!!.copy(dailyWeatherList = data.daily)
                    }
                    else -> {
                        //
                    }
                }
            }
        }
    }

}

