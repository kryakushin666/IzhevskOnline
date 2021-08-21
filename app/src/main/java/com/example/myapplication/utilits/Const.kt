package com.example.myapplication.utilits

// Firebase Database
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.myapplication.R
import com.example.myapplication.models.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.splunk.mint.Mint
import com.squareup.picasso.Picasso

lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var USER: User

const val CHILD_UID = "uid"
const val CHILD_EMAIL = "email"
const val CHILD_NAME = "name"
const val CHILD_LASTNAME = "lastname"
const val CHILD_PASSWORD = "password"

const val NODE_EXCURSION = "excursion"
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
fun displayError(message: String, view: View) {
    val snackBar = Snackbar.make(view.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
    snackBar.view.setBackgroundResource(R.drawable.curved_bg_error)
    snackBar.show()
}

fun displayMessage(message: String, view: View) {
    val snackBar = Snackbar.make(view.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
    snackBar.view.setBackgroundResource(R.drawable.curved_bg_successful)
    snackBar.show()
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