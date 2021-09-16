package com.example.myapplication.helpers

import android.content.Context
import com.example.myapplication.`interface`.Weather
import com.example.myapplication.utilits.APIWeather

class WeatherHelper: Weather {

    fun getWeather(contextCompats: Context) {
        Weather.WeatherTask(getWeatherURL(), contextCompats).execute()
        return
    }

    private fun getWeatherURL(): String {
        return "https://api.weatherapi.com/v1/current.json?key=$APIWeather&q=Izhevsk&aqi=no"
    }
    fun checkWeather(maintemp: Int): String {
        return when (maintemp) {
            maintemp.coerceIn(0, 10) -> {
                "На улице прохладно, для прогулки оденьте ветровку"
            }
            maintemp.coerceIn(11, 20) -> {
                "На улице тепло, самое то для прогулок"
            }
            maintemp.coerceIn(21, 100) -> {
                "На улице жарко, самое то для прогулок"
            }
            else -> {
                "Странно, что-то пошло не так!"
            }
        }

    }
}