package com.example.myapplication.`interface`

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.activities.bottomNavigationView
import com.example.myapplication.dialog.ErrRouteDialog
import com.example.myapplication.fragments.*
import com.example.myapplication.modulesDTO.DatabaseDTO
import com.example.myapplication.modulesDTO.GoogleMapDTO
import com.example.myapplication.modulesDTO.PlaceDTO
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

var respObjPlace: PlaceDTO = PlaceDTO()

interface Place {
    // Функция дешифровки URL
    class GetPlace(private val url: String, private val fragmentManager: FragmentManager, private val value: () -> Unit, private val activity: Activity, private val view: View) : AsyncTask<String, Void, String>() {
        @SuppressLint("WrongThread")
        override fun doInBackground(vararg params: String?): String {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()

            Log.d("GooglePlace", " data : $data")
            try {
                respObjPlace = Gson().fromJson(data, PlaceDTO::class.java)
                when (respObjPlace.status) {
                    "ZERO_RESULTS" -> {
                        activity.runOnUiThread {
                            view.findViewById<TextView>(R.id.notExist).visibility = View.VISIBLE
                            view.findViewById<TextView>(R.id.notFound).visibility = View.INVISIBLE
                        }
                    }
                    "OK" -> {
                        value()
                    }
                    else -> {
                        ErrRouteDialog {}.show(fragmentManager, "MyCustomFragment")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return "result"
        }

        // Отрисовка линий маршрута на карте
        override fun onPostExecute(result: String?) {
        }
    }


}