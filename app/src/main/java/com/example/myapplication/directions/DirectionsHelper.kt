package com.example.myapplication.directions

import androidx.fragment.app.FragmentManager
import com.example.myapplication.`interface`.Directions
import com.example.myapplication.utilits.APIMap

class DirectionsHelper(private val fragmentManager: FragmentManager): Directions {
    fun getTwoDirection(origin: String, dest: String) {
        Directions.GetDirection(getDirectionURL(origin, dest), fragmentManager).execute()
        return
    }

    fun getMoreDirection(origin: String, dest: String, waypoints: String) {
        Directions.GetDirection(getDirectionURLs(origin, dest, waypoints), fragmentManager).execute()
        return
    }

    // Функция получения запроса на пострение маршрута
    private fun getDirectionURL(origin: String, dest: String): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin}&destination=${dest}&language=ru&alternatives=true&key=$APIMap"
    }

    private fun getDirectionURLs(origin: String, dest: String, waypoints: String): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin}&destination=${dest}&waypoints=via:${waypoints}&key=$APIMap"
    }
}