package com.example.myapplication.`interface`

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.myapplication.modulesDTO.WeatherDTO
import com.example.myapplication.notification.NotificationHelper
import com.example.myapplication.weather.WeatherHelper
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

interface Weather {
    class WeatherTask(private val url: String, private val contextCompats: Context) : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()
            Log.d("Weather", " data : $data")
            try {
                val respObj = Gson().fromJson(data, WeatherDTO::class.java)
                val mainTemp = respObj.current.temp_c.toInt()
                Log.d("temp", "$mainTemp")
                NotificationHelper().sendNotification(WeatherHelper().checkWeather(mainTemp), contextCompats)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return "true"
        }

        // Отрисовка линий маршрута на карте
        override fun onPostExecute(result: String?) {
        }
    }
}