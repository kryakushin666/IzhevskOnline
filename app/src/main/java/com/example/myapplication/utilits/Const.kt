package com.example.myapplication.utilits

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.ImageView
import com.example.myapplication.R
import com.splunk.mint.Mint
import com.squareup.picasso.Picasso

const val CHILD_EMAIL = "email"
const val CHILD_NAME = "name"
const val CHILD_LASTNAME = "lastname"
const val CHILD_PASSWORD = "password"
// Picasso DI
fun ImageView.downloadAndInto(UrlString: String) {
    Picasso.get()
        .load(UrlString)
        .placeholder(R.drawable.ic_error_image)
        .error(R.drawable.ic_error_image)
        .into(this)
}

// Mint init
fun initMint(app: Application) {
    Mint.initAndStartSession(app, "e35d8a22")
    Mint.enableLogging(true)
}

fun isNetworkAvailable(context: Context?): Boolean {
    val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val n = cm.activeNetwork
        if (n != null) {
            val nc = cm.getNetworkCapabilities(n)
            //It will check for both wifi and cellular network
            return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
        return false
    } else {
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}

fun editData(contextCompats: Context, nameSharedPreferences: String, nameToData: String, defoult: String, action: String): String? {
    val pref = contextCompats.getSharedPreferences(nameSharedPreferences, Context.MODE_PRIVATE)
    val editor = pref?.edit()
    when(action) {
        "putInt" -> {
            editor?.putInt(nameToData, defoult.toInt())
            editor?.apply()
        }
        "getInt" -> {
            val result = pref!!.getInt(nameToData, defoult.toInt()).toString()
            editor?.apply()
            return result
        }
        "putString" -> {
            editor?.putString(nameToData, defoult)
            editor?.apply()
        }
        "getString" -> {
            val result = pref!!.getString(nameToData, defoult)
            editor?.apply()
            return result
        }
    }
    return null
}

//
// Other Const
// MapsFragment
const val zoomLevel = 11.5F //Зум карты
const val latitudeStartMap = 56.85970797942636 // Координата Ижевска для начальной позиции карты
const val longitudeStartMap = 53.196807013800594 // Координата Ижевска для начальной позиции карты

const val APIWeather = "7837c70818904b4eb94100007211104" // API key погоды 7837c70818904b4eb94100007211104
const val APIMap = "AIzaSyDVGH9AfUwk5CLr76_QGmoLhDNWwuj6yps" // API key карты