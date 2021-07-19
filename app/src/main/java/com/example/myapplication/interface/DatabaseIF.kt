package com.example.myapplication.`interface`

import android.os.AsyncTask
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.myapplication.modulesDTO.DatabaseDTO
import com.example.myapplication.modulesDTO.DatabaseErrorDTO
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
var respObjDatabase: DatabaseDTO = DatabaseDTO()

interface DatabaseIF {
    // Функция дешифровки URL
    class GetData(private val url: String, private val fragmentManager: FragmentManager, private val value: () -> Unit) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()
            Log.d("Database", " data : $data")
            if (data.contains("[", ignoreCase = true)) {
                respObjDatabase = Gson().fromJson(data, DatabaseDTO::class.java)
                try {
                    //Log.d("Database", " name : ${respObjDatabase[0].email}")
                    //NotificationHelper().sendNotification(WeatherHelper().checkWeather(maintemp), contextCompats)
                    value()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                val respObj = Gson().fromJson(data, DatabaseErrorDTO::class.java)
                if (respObj.errno == -1 || respObj.message == "std") {
                    Log.d("Database", "Bad input")
                }
            }
            return "true"
        }
        // Отрисовка линий маршрута на карте
        override fun onPostExecute(result: String?) {
        }
    }
}