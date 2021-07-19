package com.example.myapplication.`interface`

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request

interface EmailIF {
    class EmailTask(private val url: String, private val contextCompats: Context) : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()
            try {
                /*val respObj = Gson().fromJson(data, WeatherDTO::class.java)
                val maintemp = respObj.current.temp_c.toInt()
                Log.d("temp", "$maintemp")
                NotificationHelper().sendNotification(WeatherHelper().checkWeather(maintemp), contextCompats)*/
                Log.d("Email", " data : $data")
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