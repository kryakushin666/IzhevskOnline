package com.example.myapplication.utilits

// Firebase Database
import android.app.Application
import com.example.myapplication.models.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.splunk.mint.Mint

lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var USER: User

const val NODE_USERS = "users"
const val CHILD_UID = "uid"
const val CHILD_EMAIL = "email"
const val CHILD_NAME = "name"
const val CHILD_LASTNAME = "lastname"
const val CHILD_PASSWORD = "password"

const val NODE_SECONDARY = "secondary"
const val CHILD_MUSEUM = "museum"
const val CHILD_PARK = "park"
const val CHILD_HOTELS = "hotels"
const val CHILD_REST = "rest"
const val CHILD_NAME_SECONDARY = "name"
const val CHILD_LATLNG = "latlng"
const val CHILD_IMAGE = "image"

const val NODE_EXCURSION = "excursion"
const val CHILD_NAME_EXC = "name"

const val FOLDER_OBJECT_IMAGE = "object_image"


fun initFirebase() {
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
    USER = User()
}

// Mint init
fun initMint(app: Application) {
    Mint.initAndStartSession(app, "e35d8a22")
    Mint.enableLogging(true)
}

// Other Const
// MapsFragment
const val zoomLevel = 11.5F //Зум карты
const val latitudeStartMap = 56.85970797942636 // Координата Ижевска для начальной позиции карты
const val longitudeStartMap = 53.196807013800594 // Координата Ижевска для начальной позиции карты

const val APIWeather = "7837c70818904b4eb94100007211104" // API key погоды 7837c70818904b4eb94100007211104
const val APIMap = "AIzaSyDVGH9AfUwk5CLr76_QGmoLhDNWwuj6yps" // API key карты

// Colors
fun changeAllColor() {
}