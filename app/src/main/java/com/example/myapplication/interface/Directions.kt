package com.example.myapplication.`interface`

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import com.example.myapplication.activities.bottomNavigationView
import com.example.myapplication.dialog.ErrRouteDialog
import com.example.myapplication.fragments.*
import com.example.myapplication.modulesDTO.GoogleMapDTO
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.net.ssl.*


interface Directions {
    // Функция дешифровки URL
    class GetDirection(private val url: String, private val fragmentManager: FragmentManager, private val activity: Activity) : AsyncTask<String, Void, String>() {
        @SuppressLint("WrongThread")
        override fun doInBackground(vararg params: String?): String {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()

            Log.d("GoogleMap", " data : $data")
            val result = ArrayList<List<LatLng>>()
            try {
                val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)
                val path = ArrayList<LatLng>()
                val results = respObj.status
                if (results == "OK") {
                    val duration = respObj.routes[0].legs[0].duration.text
                    val distance = respObj.routes[0].legs[0].distance.text

                    //dirtext = duration
                    //disttext = distance
                   /* val duration2 = respObj.routes[1].legs[0].duration.text
                    val distance2 = respObj.routes[1].legs[0].distance.text
                    val duration3 = respObj.routes[2].legs[0].duration.text
                    val distance3 = respObj.routes[2].legs[0].distance.text*/
                    /*minut1.text = duration
                    minut2.text = duration2
                    minut3.text = duration3
                    km1.text = distance
                    km2.text = distance2
                    km3.text = distance3
                    Log.d("path", respObj.routes[0].legs[0].steps.size.toString())
                    if(respObj.routes[0].legs[0].steps.size >= 2){
                        for (i in 0 until respObj.routes[0].legs[0].steps.size) {
                            path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                        }
                    }
                    else {
                        for (i in 0 until respObj.routes[1].legs[0].steps.size) {
                            path.addAll(decodePolyline(respObj.routes[1].legs[0].steps[i].polyline.points))
                        }
                    }

                    mBottomSheetRoute.state = BottomSheetBehavior.STATE_EXPANDED*/
                    /*for (i in 0 until respObj.routes[0].legs[0].steps.size) {
                        path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                    }*/
                    for(i in 0 until respObj.routes[0].legs.size) {
                        for (g in 0 until respObj.routes[0].legs[i].steps.size) {
                            path.addAll(decodePolyline(respObj.routes[0].legs[i].steps[g].polyline.points))
                        }
                    }
                    result.add(path)
                    createPolyline(result)
                } else {
                    ErrRouteDialog {}.show(fragmentManager, "MyCustomFragment")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return "result"
        }
        private fun decodePolyline(encoded: String): List<LatLng> {
            val poly = ArrayList<LatLng>()
            var index = 0
            val len = encoded.length
            var lat = 0
            var lng = 0

            while (index < len) {
                var b: Int
                var shift = 0
                var result = 0
                do {
                    b = encoded[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lat += dlat

                shift = 0
                result = 0
                do {
                    b = encoded[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lng += dlng

                val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
                poly.add(latLng)
            }
            return poly
        }

        private fun createPolyline(result: List<List<LatLng>>) {
            activity.runOnUiThread {
                polylineFinal?.remove()
                val lineoption = PolylineOptions()
                for (i in result.indices) {
                    lineoption.addAll(result[i])
                    lineoption.width(10f)
                    lineoption.color(Color.BLUE)
                    lineoption.geodesic(true)
                }
                polylineFinal = map.addPolyline(lineoption)
                /*lotties.playAnimation()
                val invis = lotties.isInvisible
                Log.d("dd", "invi: $invis")
                IsCheckedDone = true*/
                //val alltext = dirtext
                //textdistance.text = alltext
                //mBottomSheetTimeRoute.visibility = View.VISIBLE
            }
        }
        // Отрисовка линий маршрута на карте
        override fun onPostExecute(result: String?) {
        }
    }


}