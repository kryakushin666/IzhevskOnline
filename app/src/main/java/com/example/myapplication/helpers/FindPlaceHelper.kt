package com.example.myapplication.helpers

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.example.myapplication.`interface`.FindPlace
import com.example.myapplication.`interface`.Place
import com.example.myapplication.utilits.APIMap

class FindPlaceHelper(private val fragmentManager: FragmentManager, private val activity: Activity, private val value: () -> Unit) : FindPlace {
    fun getFindPlace(input: String) {
        FindPlace.GetFindPlace(getFindPlaceURL(input), fragmentManager, activity) { value() }.execute()
        return
    }

    // Функция получения запроса на пострение маршрута
    private fun getFindPlaceURL(input: String): String {
        return "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=$input&inputtype=textquery&language=ru&fields=formatted_address,name,geometry&key=$APIMap"
    }
}