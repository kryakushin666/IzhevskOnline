package com.example.myapplication.`interface`

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.myapplication.dialog.NoConnectionDialog
import com.example.myapplication.utilits.isNetworkAvailable
import okhttp3.OkHttpClient
import okhttp3.Request
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface EmailIF {
    class EmailTask(private val url: String, private val fragmentManager: FragmentManager, private val activity: Activity) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            when(isNetworkAvailable(activity.applicationContext)) {
                false -> {
                    NoConnectionDialog().show(fragmentManager, "MyCustomFragment")
                }
                true -> {
                    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                        override fun checkClientTrusted(
                            chain: Array<out X509Certificate>?,
                            authType: String?
                        ) {
                        }

                        override fun checkServerTrusted(
                            chain: Array<out X509Certificate>?,
                            authType: String?
                        ) {
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
                    try {
                        /*val respObj = Gson().fromJson(data, WeatherDTO::class.java)
                    val maintemp = respObj.current.temp_c.toInt()
                    Log.d("temp", "$maintemp")
                    NotificationHelper().sendNotification(WeatherHelper().checkWeather(maintemp), contextCompats)*/
                        Log.d("Email", " data : $data")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            return "true"
        }

        // Отрисовка линий маршрута на карте
        override fun onPostExecute(result: String?) {
        }
    }
}