package com.example.myapplication.helpers

import android.app.Activity
import android.view.View
import androidx.fragment.app.FragmentManager
import com.example.myapplication.`interface`.Place
import com.example.myapplication.utilits.APIMap

class PlaceHelper(private val fragmentManager: FragmentManager, private val value: () -> Unit, private val activity: Activity, private val view: View) : Place {
    fun getPlace(latLng: String) {
        Place.GetPlace(getPlaceURL(latLng), fragmentManager, {value()}, activity, view).execute()
        return
    }

    // Функция получения запроса на пострение маршрута
    private fun getPlaceURL(latLng: String): String {
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$latLng&language=ru&radius=500&type=tourist_attraction&key=$APIMap"
    }
}