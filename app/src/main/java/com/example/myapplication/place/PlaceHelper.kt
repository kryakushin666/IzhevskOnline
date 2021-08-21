package com.example.myapplication.place

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.example.myapplication.`interface`.Directions
import com.example.myapplication.`interface`.Place
import com.example.myapplication.utilits.APIMap

class PlaceHelper(private val fragmentManager: FragmentManager, private val value: () -> Unit) : Place {
    fun getPlace(latLng: String) {
        Place.GetPlace(getPlaceURL(latLng), fragmentManager) { value() }.execute()
        return
    }

    // Функция получения запроса на пострение маршрута
    private fun getPlaceURL(latLng: String): String {
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$latLng&language=ru&radius=500&type=tourist_attraction&key=$APIMap"
    }
}