package com.example.myapplication.`interface`

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.myapplication.modulesDTO.DatabaseDTO
import com.example.myapplication.modulesDTO.DatabaseErrorDTO
import com.google.gson.Gson
import com.mklimek.sslutilsandroid.SslUtils
import okhttp3.OkHttpClient
import okhttp3.Request
import java.security.cert.X509Certificate
import javax.net.ssl.*


var respObjDatabase: DatabaseDTO = DatabaseDTO()

interface DatabaseIF {
    // Функция дешифровки URL

    class GetData(private val url: String, private val fragmentManager: FragmentManager, private val value: () -> Unit) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val client = OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }.build()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()
            Log.d("Database", " data : $data")
            if (data.contains("[", ignoreCase = true)){
                respObjDatabase = Gson().fromJson(data, DatabaseDTO::class.java)
                if(respObjDatabase.success) {
                    try {
                        //Log.d("Database", " name : ${respObjDatabase[0].email}")
                        //NotificationHelper().sendNotification(WeatherHelper().checkWeather(maintemp), contextCompats)
                        value()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else {
                    Log.d("Database", "Bad Response")
                }
            }
            else {
                val objDatabase = Gson().fromJson(data, DatabaseErrorDTO::class.java)
                if(objDatabase.success) {
                    try {
                        //Log.d("Database", " name : ${respObjDatabase[0].email}")
                        //NotificationHelper().sendNotification(WeatherHelper().checkWeather(maintemp), contextCompats)
                        value()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else {
                    Log.d("Database", "Bad Response")
                }
            }

            return "true"
        }
        // Отрисовка линий маршрута на карте
        override fun onPostExecute(result: String?) {
        }
    }
}

